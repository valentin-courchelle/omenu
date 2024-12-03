package com.optimweb.omenu.service.mapper;

import com.optimweb.omenu.dto.RecipeDto;
import com.optimweb.omenu.model.Recipe;
import org.springframework.stereotype.Service;

@Service
public class RecipeMapper {

    private RecipeMapper() {
    }

    public static RecipeDto toDto(Recipe recipe){
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .ingredients(recipe.getIngredients().stream().map(RecipeIngredientMapper::toDto).toList())
                .season(recipe.getSeason())
                .rating(recipe.getRating())
                .description(recipe.getDescription())
                .duration(recipe.getDuration())
                .nbPeople(recipe.getNbPeople())
                .build();
    }

    public static Recipe toRecipe(RecipeDto dto){
        return Recipe.builder()
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .id(dto.getId())
                .ingredients(dto.getIngredients().stream().map(RecipeIngredientMapper::toRecipeIngredient).toList())
                .name(dto.getName())
                .nbPeople(dto.getNbPeople())
                .rating(dto.getRating())
                .season(dto.getSeason())
                .build();

    }

}
