package com.optimweb.omenu.database.entity;

import com.optimweb.omenu.model.IngredientType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "ingredient_seq", allocationSize = 1)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientType type;
}
