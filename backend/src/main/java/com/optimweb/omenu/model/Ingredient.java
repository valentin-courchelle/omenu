package com.optimweb.omenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Ingredient {
    private Long id;

    private String name;

    private IngredientType type;

    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}
