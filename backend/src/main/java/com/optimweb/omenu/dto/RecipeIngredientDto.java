package com.optimweb.omenu.dto;

import com.optimweb.omenu.model.IngredientType;
import com.optimweb.omenu.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecipeIngredientDto {

    private long ingredientId;

    private String name;

    private IngredientType type;

    private float quantity;

    private Unit unit;
}
