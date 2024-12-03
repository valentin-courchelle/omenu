package com.optimweb.omenu.database.service;

import com.optimweb.omenu.database.entity.IngredientEntity;
import com.optimweb.omenu.database.repository.IngredientRepository;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
import com.optimweb.omenu.model.Ingredient;
import com.optimweb.omenu.model.IngredientType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientRepositoryService {

    public static final String NO_INGREDIENT_FOUND_WITH_ID = "No Ingredient found with id ";

    private final IngredientRepository repository;

    public Ingredient getIngredient(long id) {
        Optional<IngredientEntity> ingredientOpt = this.repository.findById(id);
        if (ingredientOpt.isEmpty()) {
            String message = NO_INGREDIENT_FOUND_WITH_ID + id;
            log.error(message);
            return null;
        }
            return this.toIngredient(ingredientOpt.get());
    }

    public boolean doesIngredientExist(String name, IngredientType type){
        return this.repository.findByNameAndType(name, type) != null;
    }

    public List<Ingredient> getAllIngredient() {
        return this.repository.findAll().stream().map(this::toIngredient).toList();
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return this.toIngredient(this.repository.save(this.toEntity(ingredient)));
    }

    public Ingredient updateIngredient(long id, Ingredient ingredient) throws NotFoundException, BadRequestException {
        Optional<IngredientEntity> optIngredient = this.repository.findById(id);
        if(optIngredient.isEmpty()){
            log.error("No ingredient found with id {}", id);
            return null;
        }
        IngredientEntity existedIngredient = optIngredient.get();
        existedIngredient.setName(ingredient.getName());
        existedIngredient.setType(ingredient.getType());
        IngredientEntity updatedIngredient = this.repository.save(existedIngredient);
        log.info("Ingredient with id {} successfully updated", ingredient.getId());
        return this.toIngredient(updatedIngredient);
    }

    public void deleteIngredient(long id) {
        this.repository.deleteById(id);
    }

    private IngredientEntity toEntity(Ingredient ingredient) {
        IngredientEntity entity = new IngredientEntity();
        entity.setName(ingredient.getName());
        entity.setType(ingredient.getType());
        return entity;
    }

    private Ingredient toIngredient(IngredientEntity entity) {
        return Ingredient.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .build();
    }

}
