package com.optimweb.omenu.dto;

import com.optimweb.omenu.model.IngredientType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private IngredientType type;

    private Integer recipeCount;
}
