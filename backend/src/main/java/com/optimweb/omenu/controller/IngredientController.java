package com.optimweb.omenu.controller;

import com.optimweb.omenu.database.service.IngredientService;
import com.optimweb.omenu.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredient(){
        return ResponseEntity.ok().body(this.ingredientService.getAllIngredient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable long id){
        return ResponseEntity.ok().body(this.ingredientService.getIngredient(id));
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient){
        return ResponseEntity.ok().body(this.ingredientService.saveIngredient(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable long id, @RequestBody Ingredient ingredient){
        return ResponseEntity.ok().body(this.ingredientService.updateIngredient(id, ingredient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id){
        this.ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
