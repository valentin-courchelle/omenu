package com.optimweb.omenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recipe {

    private Long id;

    private String name;

    private int duration;

    private float rating;

    private int nbPeople;

    private Date seasonStart;

    private Date seasonEnd;
}
