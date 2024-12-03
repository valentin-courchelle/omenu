package com.optimweb.omenu.controller;

import com.optimweb.omenu.controller.service.RecipeService;
import com.optimweb.omenu.dto.RecipeDto;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
import com.optimweb.omenu.model.IngredientType;
import com.optimweb.omenu.model.Month;
import com.optimweb.omenu.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipe() {
        return ResponseEntity.ok().body(this.recipeService.getAllRecipe());
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<List<RecipeDto>> getRecipeWithIngredient(@PathVariable("id") Long ingredientId) {
        return ResponseEntity.ok().body(this.recipeService.getRecipeByIngredientId(ingredientId));
    }

    @GetMapping("/ingredient")
    public ResponseEntity<List<RecipeDto>> getRecipeWithIngredientType(@RequestParam("type") IngredientType type) {
        return ResponseEntity.ok().body(this.recipeService.getRecipeByIngredientType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(this.recipeService.getRecipe(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/season")
    public ResponseEntity<List<RecipeDto>> getAllRecipeByMonth(@RequestParam(name = "month") Month month) {
        return ResponseEntity.ok().body(this.recipeService.getRecipeByMonth(month));
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok().body(this.recipeService.createRecipe(recipe));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable long id, @RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok().body(this.recipeService.updateRecipe(id, recipe));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        try {
            this.recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
