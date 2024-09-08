package com.optimweb.omenu.database.service;

import com.optimweb.omenu.database.entity.RecipeEntity;
import com.optimweb.omenu.database.repository.RecipeRepository;
import com.optimweb.omenu.model.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    public static final String NO_RECIPE_FOUND_WITH_ID = "No Recipe found with id ";

    private final RecipeRepository repository;

    public Recipe getRecipe(long id) {
        Optional<RecipeEntity> recipeOpt = this.repository.findById(id);
        if (recipeOpt.isPresent()) {
            return this.toRecipe(recipeOpt.get());
        }
        log.error(NO_RECIPE_FOUND_WITH_ID + id);
        return null;
    }

    public List<Recipe> getAllRecipe() {
        return this.repository.findAll().stream().map(this::toRecipe).collect(Collectors.toList());
    }

    public Recipe saveRecipe(Recipe recipe) {
        return this.toRecipe(this.repository.save(this.toEntity(recipe)));
    }

    public Recipe updateRecipe(Recipe recipe) {
        Optional<RecipeEntity> recipeOpt = this.repository.findById(recipe.getId());
        if (recipeOpt.isEmpty()) {
            log.error(NO_RECIPE_FOUND_WITH_ID + recipe.getId());
            return null;
        }
        RecipeEntity entity = recipeOpt.get();
        RecipeEntity updatedRecipe = this.toEntity(recipe);
        updatedRecipe.setId(entity.getId());
        updatedRecipe = this.repository.save(updatedRecipe);
        log.info("Recipe with id "+recipe.getId()+" successfully updated");
        return this.toRecipe(updatedRecipe);
    }

    public void deleteRecipe(long id){
        Optional<RecipeEntity> recipeOpt = this.repository.findById(id);
        if (recipeOpt.isEmpty()) {
            log.error(NO_RECIPE_FOUND_WITH_ID + id);
            return;
        }
        RecipeEntity entity = recipeOpt.get();
        this.repository.delete(entity);
        log.info("Recipe with id "+id+" successfully deleted");
    }

    private RecipeEntity toEntity(Recipe recipe) {
        RecipeEntity entity = new RecipeEntity();
        entity.setName(recipe.getName());
        entity.setDuration(recipe.getDuration());
        entity.setNbPeople(recipe.getNbPeople());
        entity.setRating(recipe.getRating());
        entity.setSeasonStart(new java.sql.Date(recipe.getSeasonStart().getTime()));
        entity.setSeasonEnd(new java.sql.Date(recipe.getSeasonEnd().getTime()));
        return entity;
    }

    private Recipe toRecipe(RecipeEntity entity) {
        return Recipe.builder()
                .id(entity.getId())
                .name(entity.getName())
                .duration(entity.getDuration())
                .nbPeople(entity.getNbPeople())
                .rating(entity.getRating())
                .seasonStart(new Date(entity.getSeasonStart().getTime()))
                .seasonEnd(new Date(entity.getSeasonEnd().getTime()))
                .build();
    }


}
