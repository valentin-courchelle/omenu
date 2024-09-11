package com.optimweb.omenu.database.service;

import com.optimweb.omenu.database.entity.IngredientEntity;
import com.optimweb.omenu.database.entity.RecipeEntity;
import com.optimweb.omenu.database.entity.RecipeIngredientEntity;
import com.optimweb.omenu.database.repository.IngredientRepository;
import com.optimweb.omenu.database.repository.RecipeIngredientRepository;
import com.optimweb.omenu.database.repository.RecipeRepository;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
import com.optimweb.omenu.model.IngredientType;
import com.optimweb.omenu.model.Month;
import com.optimweb.omenu.model.Recipe;
import com.optimweb.omenu.model.RecipeIngredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    public static final String NO_RECIPE_FOUND_WITH_ID = "No Recipe found with id ";

    private final RecipeRepository recipeRepository;

    private final IngredientRepository ingredientRepository;

    private final RecipeIngredientRepository recipeIngredientRepository;

    public Recipe getRecipe(long id) throws NotFoundException {
        Optional<RecipeEntity> recipeOpt = this.recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            String message = NO_RECIPE_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        return this.toRecipe(recipeOpt.get());
    }

    public List<Recipe> getAllRecipe() {
        return this.recipeRepository.findAll().stream().map(this::toRecipe).toList();
    }

    public Recipe saveRecipe(Recipe recipe) throws NotFoundException, BadRequestException {
        RecipeEntity entity = this.recipeRepository.findByName(recipe.getName());
        if (entity != null) {
            String message = "A recipe with this name " + recipe.getName() + " already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        return this.toRecipe(this.recipeRepository.save(this.toEntity(recipe)));
    }

    public Recipe updateRecipe(long id, Recipe recipe) throws BadRequestException, NotFoundException {
        RecipeEntity sameNameRecipe = this.recipeRepository.findByName(recipe.getName());
        if (sameNameRecipe != null && id != recipe.getId()) {
            String message = "An other recipe with name " + recipe.getName() + " already exists";
            log.error(message);
            throw new BadRequestException(message);
        }

        Optional<RecipeEntity> recipeOpt = this.recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            String message = NO_RECIPE_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }

        RecipeEntity existingRecipe = recipeOpt.get();

        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setDuration(recipe.getDuration());
        existingRecipe.setRating(recipe.getRating());
        existingRecipe.setNbPeople(recipe.getNbPeople());
        existingRecipe.setSeason(recipe.getSeason());

        List<RecipeIngredientEntity> updatedIngredients = updateIngredients(existingRecipe, recipe.getIngredients());
        existingRecipe.setIngredients(updatedIngredients);

        RecipeEntity updatedRecipe = this.recipeRepository.save(existingRecipe);
        log.info("Recipe with id " + recipe.getId() + " successfully updated");
        return this.toRecipe(updatedRecipe);
    }

    public void deleteRecipe(long id) throws NotFoundException {
        Optional<RecipeEntity> recipeOpt = this.recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            String message = NO_RECIPE_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        RecipeEntity entity = recipeOpt.get();
        this.recipeRepository.delete(entity);
        log.info("Recipe with id " + id + " successfully deleted");
    }

    /**
     * Update recipe ingredient list
     */
    private List<RecipeIngredientEntity> updateIngredients(RecipeEntity existingRecipe, List<RecipeIngredient> newIngredients) {
        List<RecipeIngredientEntity> currentIngredients = existingRecipe.getIngredients();

        // Delete former ingredient which are not present anymore
        currentIngredients.removeIf(currentIngredient -> newIngredients.stream()
                .noneMatch(newIngredient -> newIngredient.getIngredientId() == currentIngredient.getIngredient().getId()));

        // Add or update new ingredients
        for (RecipeIngredient newRecipeIngredient : newIngredients) {
            Optional<RecipeIngredientEntity> existingIngredientOpt = currentIngredients.stream()
                    .filter(currentIngredient -> currentIngredient.getIngredient().getId() == newRecipeIngredient.getIngredientId())
                    .findFirst();

            if (existingIngredientOpt.isPresent()) {
                // Update existing infregient
                RecipeIngredientEntity existingIngredient = existingIngredientOpt.get();
                existingIngredient.setQuantity(newRecipeIngredient.getQuantity());
                existingIngredient.setUnit(newRecipeIngredient.getUnit());
            } else {
                // Add new ingredient
                RecipeIngredientEntity newIngredientEntity = new RecipeIngredientEntity();
                Optional<IngredientEntity> ingredientOpt = this.ingredientRepository.findById(newRecipeIngredient.getIngredientId());
                if (ingredientOpt.isPresent()) {
                    newIngredientEntity.setIngredient(ingredientOpt.get());
                    newIngredientEntity.setQuantity(newRecipeIngredient.getQuantity());
                    newIngredientEntity.setUnit(newRecipeIngredient.getUnit());
                    newIngredientEntity.setRecipe(existingRecipe);
                    currentIngredients.add(newIngredientEntity);
                } else {
                    log.error("No ingredient found with id " + newRecipeIngredient.getIngredientId());
                }

            }
        }

        return currentIngredients;
    }

    private RecipeEntity toEntity(Recipe recipe) throws NotFoundException {
        RecipeEntity entity = new RecipeEntity();
        entity.setName(recipe.getName());
        entity.setDescription(recipe.getDescription());
        entity.setDuration(recipe.getDuration());
        entity.setNbPeople(recipe.getNbPeople());
        entity.setRating(recipe.getRating());
        entity.setSeason(recipe.getSeason());
        List<RecipeIngredientEntity> recipeIngredientEntities = new ArrayList<>();
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            recipeIngredientEntities.add(this.toEntity(recipeIngredient, entity));
        }
        entity.setIngredients(recipeIngredientEntities);
        return entity;
    }

    private RecipeIngredientEntity toEntity(RecipeIngredient ingredient, RecipeEntity recipeEntity) throws NotFoundException {
        RecipeIngredientEntity entity = new RecipeIngredientEntity();
        entity.setRecipe(recipeEntity);
        entity.setUnit(ingredient.getUnit());
        entity.setQuantity(ingredient.getQuantity());
        Optional<IngredientEntity> ingredientOpt = this.ingredientRepository.findById(ingredient.getIngredientId());
        if (ingredientOpt.isEmpty()) {
            String message = "No ingredient found with id" + ingredient.getIngredientId();
            log.error(message);
            throw new NotFoundException(message);
        } else {
            IngredientEntity ingredientEntity = ingredientOpt.get();
            ingredientEntity.getRecipeIngredients().add(entity);
            entity.setIngredient(ingredientEntity);
        }
        return entity;
    }

    private Recipe toRecipe(RecipeEntity entity) {
        return Recipe.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .duration(entity.getDuration())
                .nbPeople(entity.getNbPeople())
                .rating(entity.getRating())
                .season(entity.getSeason())
                .ingredients(entity.getIngredients().stream().map(this::toIngredient).toList())
                .build();
    }

    private RecipeIngredient toIngredient(RecipeIngredientEntity recipeIngredientEntity) {
        return RecipeIngredient.builder()
                .ingredientId(recipeIngredientEntity.getIngredient().getId())
                .type(recipeIngredientEntity.getIngredient().getType())
                .unit(recipeIngredientEntity.getUnit())
                .quantity(recipeIngredientEntity.getQuantity())
                .name(recipeIngredientEntity.getIngredient().getName())
                .build();
    }

    public List<Recipe> getRecipeByIngredientId(Long ingredientId) {
        Optional<IngredientEntity> ingredientOpt = this.ingredientRepository.findById(ingredientId);
        if (ingredientOpt.isEmpty()) {
            log.error("No ingredient found with id " + ingredientId);
            return Collections.emptyList();
        }
        IngredientEntity ingredientEntity = ingredientOpt.get();
        List<RecipeIngredientEntity> recipeIngredientEntities = this.recipeIngredientRepository.findByIngredient(ingredientEntity);
        return recipeIngredientEntities.stream().map(RecipeIngredientEntity::getRecipe).map(this::toRecipe).toList();
    }

    public List<Recipe> getRecipeByMonth(Month month) {
        return this.recipeRepository.findAllBySeason(month).stream().map(this::toRecipe).toList();
    }

    public List<Recipe> getRecipeByIngredientType(IngredientType type) {
        List<IngredientEntity> ingredients = this.ingredientRepository.findAllByType(type);
        List<RecipeEntity> entities = this.recipeIngredientRepository.findAllByIngredientIn(ingredients).stream().map(RecipeIngredientEntity::getRecipe).toList();
        return entities.stream().map(this::toRecipe).toList();
    }
}
