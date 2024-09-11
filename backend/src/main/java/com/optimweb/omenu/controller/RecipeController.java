package com.optimweb.omenu.controller;

import com.optimweb.omenu.database.service.RecipeService;
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
    public ResponseEntity<List<Recipe>> getAllRecipe() {
        return ResponseEntity.ok().body(this.recipeService.getAllRecipe());
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<List<Recipe>> getRecipeWithIngredient(@PathVariable("id") Long ingredientId) {
        return ResponseEntity.ok().body(this.recipeService.getRecipeByIngredientId(ingredientId));
    }

    @GetMapping("/ingredient")
    public ResponseEntity<List<Recipe>> getRecipeWithIngredientType(@RequestParam("type") IngredientType type) {
        return ResponseEntity.ok().body(this.recipeService.getRecipeByIngredientType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(this.recipeService.getRecipe(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/season")
    public ResponseEntity<List<Recipe>> getAllRecipeByMonth(@RequestParam(name = "month") Month month) {
        return ResponseEntity.ok().body(this.recipeService.getRecipeByMonth(month));
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok().body(this.recipeService.saveRecipe(recipe));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
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
