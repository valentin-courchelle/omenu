package com.optimweb.omenu.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    /**
     * Duration of the recipe in minutes
     */
    private int duration;

    /**
     * Between 0.0 and 5.0
     */
    private float rating;

    private int nbPeople;

    private Date seasonStart;

    private Date seasonEnd;

    //TODO
    //private List<Ingredient> ingredients;


}
