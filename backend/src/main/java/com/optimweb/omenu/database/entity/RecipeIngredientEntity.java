package com.optimweb.omenu.database.entity;

import com.optimweb.omenu.model.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_ing_seq")
    @SequenceGenerator(name = "recipe_ing_seq", sequenceName = "recipe_ing_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn( name="ingredient_id" )
    private IngredientEntity ingredient;

    private float quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;
}
