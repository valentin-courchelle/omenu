package com.optimweb.omenu.service.mapper;

import com.optimweb.omenu.dto.RecipeIngredientDto;
import com.optimweb.omenu.model.RecipeIngredient;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientMapper {

    private RecipeIngredientMapper() {
    }

    public static RecipeIngredientDto toDto(RecipeIngredient recipeIngredient){
        return RecipeIngredientDto.builder()
                .ingredientId(recipeIngredient.getIngredientId())
                .unit(recipeIngredient.getUnit())
                .type(recipeIngredient.getType())
                .name(recipeIngredient.getName())
                .quantity(recipeIngredient.getQuantity())
                .build();
    }

    public static RecipeIngredient toRecipeIngredient(RecipeIngredientDto dto){
        return RecipeIngredient.builder()
                .ingredientId(dto.getIngredientId())
                .unit(dto.getUnit())
                .type(dto.getType())
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .build();
    }
}
