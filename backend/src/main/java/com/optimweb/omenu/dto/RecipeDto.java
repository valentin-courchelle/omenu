package com.optimweb.omenu.dto;

import com.optimweb.omenu.model.Month;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private int duration;

    @NotNull
    private float rating;

    @NotNull
    private int nbPeople;

    @NotNull
    private List<Month> season;

    @NotNull
    @NotEmpty
    private List<RecipeIngredientDto> ingredients;
}
