package com.optimweb.omenu.controller.service;

import com.optimweb.omenu.database.service.RecipeRepositoryService;
import com.optimweb.omenu.dto.RecipeDto;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
import com.optimweb.omenu.model.IngredientType;
import com.optimweb.omenu.model.Month;
import com.optimweb.omenu.model.Recipe;
import com.optimweb.omenu.service.mapper.RecipeIngredientMapper;
import com.optimweb.omenu.service.mapper.RecipeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepositoryService repositoryService;

    public static final String NO_RECIPE_FOUND_WITH_ID = "No Recipe found with id ";

    public List<RecipeDto> getAllRecipe() {
        return this.repositoryService.getAllRecipe().stream().map(RecipeMapper::toDto).toList();
    }

    public List<RecipeDto> getRecipeByIngredientId(Long ingredientId) {
        return this.repositoryService.getRecipeByIngredientId(ingredientId).stream().map(RecipeMapper::toDto).toList();
    }

    public List<RecipeDto> getRecipeByIngredientType(IngredientType type) {
        return this.repositoryService.getRecipeByIngredientType(type).stream().map(RecipeMapper::toDto).toList();
    }

    public RecipeDto getRecipe(long id) throws NotFoundException {
        Recipe recipe = this.repositoryService.getRecipe(id);
        if (recipe == null) {
            String message = NO_RECIPE_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        return RecipeMapper.toDto(recipe);
    }

    public List<RecipeDto> getRecipeByMonth(Month month) {
        return this.repositoryService.getRecipeByMonth(month).stream().map(RecipeMapper::toDto).toList();
    }

    public RecipeDto createRecipe(RecipeDto recipe) throws NotFoundException, BadRequestException {
        if (this.repositoryService.doesRecipeExist(recipe.getName())) {
            String message = "A recipe with this name " + recipe.getName() + " already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        return RecipeMapper.toDto(this.repositoryService.saveRecipe(RecipeMapper.toRecipe(recipe)));
    }

    public RecipeDto updateRecipe(long id, RecipeDto recipe) throws NotFoundException, BadRequestException {
        Recipe existingRecipe = this.repositoryService.getRecipe(id);
        if(existingRecipe == null){
            String message = NO_RECIPE_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        if(!existingRecipe.getName().equals(recipe.getName()) && this.repositoryService.doesRecipeExist(recipe.getName())){
            String message = "A recipe with this name " + recipe.getName() + " already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        return RecipeMapper.toDto(this.repositoryService.updateRecipe(id, RecipeMapper.toRecipe(recipe)));
    }

    public void deleteRecipe(long id) throws NotFoundException {
        if(this.repositoryService.getRecipe(id) == null){
            String message = NO_RECIPE_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        this.repositoryService.deleteRecipe(id);
        log.info("Recipe with id {} successfully deleted", id);
    }
}
