package com.optimweb.omenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recipe {

    private Long id;

    private String name;

    private String description;

    private int duration;

    private float rating;

    private int nbPeople;

    private Date seasonStart;

    private Date seasonEnd;

    private List<RecipeIngredient> ingredients;
}
