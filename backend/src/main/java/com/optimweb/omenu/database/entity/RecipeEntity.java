package com.optimweb.omenu.database.entity;

import com.optimweb.omenu.model.Month;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipe_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    /**
     * Duration of the recipe in minutes
     */
    private int duration;

    /**
     * Between 0.0 and 5.0
     */
    private float rating;

    @Column(name = "nb_people")
    private int nbPeople;

    @ElementCollection(targetClass = Month.class)
    @CollectionTable(name = "recipe_season", joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private List<Month> season;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "recipe",
            fetch = FetchType.EAGER)
    private List<RecipeIngredientEntity> ingredients;


}
