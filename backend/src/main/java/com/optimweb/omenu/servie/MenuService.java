package com.optimweb.omenu.servie;

import com.optimweb.omenu.database.service.RecipeService;
import com.optimweb.omenu.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private RecipeService recipeService;

    public record MealGeneratorRecord(int nbMeal, List<Month> months) {
    }


    public Menu generateMenu(Map<Date, List<MealTime>> mealTimeByDate) {
        // Be sure that every date represents a distinct day + gather distinct meal time to related day
        Map<LocalDate, Set<MealTime>> cleanedMealTimeByDate = cleanMealTimesByDate(mealTimeByDate);

        MealGeneratorRecord mealsGeneratorParams = this.computeGeneratorParams(cleanedMealTimeByDate);
        List<Recipe> recipes = this.generateMeals(mealsGeneratorParams);
        return this.createMenu(recipes, cleanedMealTimeByDate);
    }

    private Menu createMenu(List<Recipe> recipes, Map<LocalDate, Set<MealTime>> mealTimeByDate) {
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
        return new Menu(mealsByDate);
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
            recipeForMonths.addAll(this.recipeService.getRecipeByMonth(month));
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
