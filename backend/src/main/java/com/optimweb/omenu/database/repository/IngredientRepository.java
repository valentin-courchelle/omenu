package com.optimweb.omenu.database.repository;

import com.optimweb.omenu.database.entity.IngredientEntity;
import com.optimweb.omenu.model.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    List<IngredientEntity> findAllByType(IngredientType type);

    IngredientEntity findByNameAndType(String name, IngredientType type);
}
