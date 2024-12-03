package com.optimweb.omenu.service;

import com.optimweb.omenu.database.service.RecipeRepositoryService;
import com.optimweb.omenu.dto.MealDto;
import com.optimweb.omenu.dto.MenuDto;
import com.optimweb.omenu.dto.RecipeIngredientDto;
import com.optimweb.omenu.model.*;
import com.optimweb.omenu.service.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final RecipeRepositoryService recipeRepositoryService;

    public record MealGeneratorRecord(int nbMeal, List<Month> months) {
    }

    public record RecipeIngredientKeys(long ingredientId, Unit unit) {
    }


    public MenuDto generateMenu(Map<Date, List<MealTime>> mealTimeByDate) {
        // Be sure that every date represents a distinct day + gather distinct meal time to related day
        Map<LocalDate, Set<MealTime>> cleanedMealTimeByDate = cleanMealTimesByDate(mealTimeByDate);

        MealGeneratorRecord mealsGeneratorParams = this.computeGeneratorParams(cleanedMealTimeByDate);
        List<Recipe> recipes = this.generateMeals(mealsGeneratorParams);
        return this.createMenu(recipes, cleanedMealTimeByDate);
    }

    public List<RecipeIngredientDto> generateIngredientList(MenuDto menu) {
        Map<RecipeIngredientKeys, RecipeIngredientDto> ingredientsByKeys = new HashMap<>();
        for (List<MealDto> meals : menu.getMeals().values()) {
            for (MealDto meal : meals) {
                List<RecipeIngredientDto> recipeIngredients = meal.getRecipe().getIngredients();
                for (RecipeIngredientDto recipeIngredient : recipeIngredients) {
                    RecipeIngredientKeys keys = new RecipeIngredientKeys(recipeIngredient.getIngredientId(), recipeIngredient.getUnit());
                    if (ingredientsByKeys.containsKey(keys)) {
                        RecipeIngredientDto previousRecipeIngredient = ingredientsByKeys.get(keys);
                        previousRecipeIngredient.setQuantity(previousRecipeIngredient.getQuantity() + recipeIngredient.getQuantity());
                    } else {
                        ingredientsByKeys.put(keys, recipeIngredient);
                    }
                }
            }
        }
        return ingredientsByKeys.values().stream().toList();
    }

    private MenuDto createMenu(List<Recipe> recipes, Map<LocalDate, Set<MealTime>> mealTimeByDate) {
        Map<Date, List<Meal>> mealsByDate = new HashMap<>();
        int index = 0;
        for (Map.Entry<LocalDate, Set<MealTime>> entry : mealTimeByDate.entrySet()) {
            LocalDate day = entry.getKey();
            Set<MealTime> mealTimes = entry.getValue();
            for (MealTime time : mealTimes) {
                Recipe recipe = recipes.get(index);
                Meal meal = this.toMeal(recipe, time);
                Date date = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (mealsByDate.containsKey(date)) {
                    mealsByDate.get(date).add(meal);
                } else {
                    List<Meal> meals = new ArrayList<>();
                    meals.add(meal);
                    mealsByDate.put(date, meals);
                }
                index++;
                if (index >= recipes.size()) {
                    index = 0;
                }
            }
        }
        return MenuMapper.toDto(new Menu(mealsByDate));
    }

    private Meal toMeal(Recipe recipe, MealTime time) {
        return Meal.builder()
                .recipe(recipe)
                .time(time)
                .build();
    }

    private List<Recipe> generateMeals(MealGeneratorRecord params) {
        Set<Recipe> recipeForMonths = new HashSet<>();
        for (Month month : params.months()) {
            recipeForMonths.addAll(this.recipeRepositoryService.getRecipeByMonth(month));
        }
        if (recipeForMonths.size() < params.nbMeal()) {
            return new ArrayList<>(recipeForMonths);
        }

        ArrayList<Recipe> list = new ArrayList<>(recipeForMonths);
        Collections.shuffle(list, new Random());
        return list.subList(0, params.nbMeal());
    }

    private MealGeneratorRecord computeGeneratorParams(Map<LocalDate, Set<MealTime>> mealTimesByDate) {
        Set<Month> monthSet = mealTimesByDate.keySet().stream().map(d -> Month.valueOf(d.getMonth().toString())).collect(Collectors.toSet());
        int nbMeal = mealTimesByDate.values().stream()
                .mapToInt(Set::size)
                .sum();
        return new MealGeneratorRecord(nbMeal, new ArrayList<>(monthSet));
    }

    private Map<LocalDate, Set<MealTime>> cleanMealTimesByDate(Map<Date, List<MealTime>> mealTimesByDate) {
        Map<LocalDate, Set<MealTime>> cleanedMealTimeByDate = new HashMap<>();
        for (Map.Entry<Date, List<MealTime>> entry : mealTimesByDate.entrySet()) {
            LocalDate day = entry.getKey().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!cleanedMealTimeByDate.containsKey(day)) {
                cleanedMealTimeByDate.put(day, new HashSet<>(entry.getValue()));
            } else {
                cleanedMealTimeByDate.get(day).addAll(new HashSet<>(entry.getValue()));
            }
        }
        return cleanedMealTimeByDate;
    }
}
