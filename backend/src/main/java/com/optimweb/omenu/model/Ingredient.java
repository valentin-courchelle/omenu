package com.optimweb.omenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Ingredient {
    private long id;

    private String name;

    private IngredientType type;
}
