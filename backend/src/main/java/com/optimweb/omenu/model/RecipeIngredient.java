package com.optimweb.omenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecipeIngredient {

    private long ingredientId;

    private String name;

    private IngredientType type;

    private float quantity;

    private Unit unit;
}
