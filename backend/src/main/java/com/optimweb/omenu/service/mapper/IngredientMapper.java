package com.optimweb.omenu.service.mapper;

import com.optimweb.omenu.dto.IngredientDto;
import com.optimweb.omenu.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IngredientMapper {

    private IngredientMapper() {
    }

    public static IngredientDto toDto(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .type(ingredient.getType())
                .recipeCount(ingredient.getRecipeIngredients().size())
                .build();
    }

    public static Ingredient toIngredient(IngredientDto ingredient){
        return Ingredient.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .type(ingredient.getType())
                .recipeIngredients(new ArrayList<>())
                .build();
    }

}
