package com.optimweb.omenu.database.repository;

import com.optimweb.omenu.database.entity.RecipeEntity;
import com.optimweb.omenu.model.Month;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    List<RecipeEntity> findAllBySeason(Month month);
}
