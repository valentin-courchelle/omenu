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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
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

    @ElementCollection(targetClass = Month.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_season", joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    private List<Month> season;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredientEntity> ingredients;


}
