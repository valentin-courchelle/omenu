package com.optimweb.omenu.service.mapper;

import com.optimweb.omenu.dto.MealDto;
import com.optimweb.omenu.dto.MenuDto;
import com.optimweb.omenu.model.Meal;
import com.optimweb.omenu.model.Menu;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuMapper {

    private MenuMapper() {
    }

    public static MenuDto toDto(Menu menu) {
        Map<Date, List<MealDto>> mealMap = new HashMap<>();
        menu.getMeals().forEach((d, l) -> {
            List<MealDto> mealDtos = l.stream().map(m -> MealDto.builder().recipe(RecipeMapper.toDto(m.getRecipe())).build()).toList();
            mealMap.put(d, mealDtos);
        });
        return MenuDto.builder()
                .meals(mealMap)
                .build();
    }

    public static Menu toMenu(MenuDto dto) {
        Map<Date, List<Meal>> mealMap = new HashMap<>();
        dto.getMeals().forEach((d, l) -> {
            List<Meal> meals = l.stream().map(m -> Meal.builder().recipe(RecipeMapper.toRecipe(m.getRecipe())).build()).toList();
            mealMap.put(d, meals);
        });
        return Menu.builder()
                .meals(mealMap)
                .build();
    }
}
