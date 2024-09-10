package com.optimweb.omenu.database.repository;

import com.optimweb.omenu.database.entity.IngredientEntity;
import com.optimweb.omenu.database.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {
    List<RecipeIngredientEntity> findByIngredient(IngredientEntity ingredientEntity);

    List<RecipeIngredientEntity> findAllByIngredientIn(List<IngredientEntity> ingredients);
}
