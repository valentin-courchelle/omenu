package com.optimweb.omenu.database.repository;

import com.optimweb.omenu.database.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
}
