package com.optimweb.omenu.controller;

import com.optimweb.omenu.controller.service.IngredientService;
import com.optimweb.omenu.dto.IngredientDto;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
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
    public ResponseEntity<List<IngredientDto>> getAllIngredient() {
        return ResponseEntity.ok().body(this.ingredientService.getAllIngredient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(this.ingredientService.getIngredient(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok().body(this.ingredientService.createIngredient(ingredient));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable long id, @RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok().body(this.ingredientService.updateIngredient(id, ingredient));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        try {
            this.ingredientService.deleteIngredient(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
