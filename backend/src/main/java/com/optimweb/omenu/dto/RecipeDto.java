package com.optimweb.omenu.dto;

import com.optimweb.omenu.model.Month;
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

    private String name;

    private String description;

    private int duration;

    private float rating;

    private int nbPeople;

    private List<Month> season;

    private List<RecipeIngredientDto> ingredients;
}
