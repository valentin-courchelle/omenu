package com.optimweb.omenu.database.service;

import com.optimweb.omenu.database.entity.IngredientEntity;
import com.optimweb.omenu.database.repository.IngredientRepository;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
import com.optimweb.omenu.model.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

    public static final String NO_INGREDIENT_FOUND_WITH_ID = "No Ingredient found with id ";

    private final IngredientRepository repository;

    public Ingredient getIngredient(long id) throws NotFoundException {
        Optional<IngredientEntity> ingredientOpt = this.repository.findById(id);
        if (ingredientOpt.isEmpty()) {
            String message = NO_INGREDIENT_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
            return this.toIngredient(ingredientOpt.get());
    }

    public List<Ingredient> getAllIngredient() {
        return this.repository.findAll().stream().map(this::toIngredient).toList();
    }

    public Ingredient saveIngredient(Ingredient ingredient) throws BadRequestException {
        IngredientEntity entity = this.repository.findByNameAndType(ingredient.getName(), ingredient.getType());
        if(entity != null){
            String message = "An ingredient with the same name and type already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        return this.toIngredient(this.repository.save(this.toEntity(ingredient)));
    }

    public Ingredient updateIngredient(long id, Ingredient ingredient) throws NotFoundException, BadRequestException {
        IngredientEntity sameNameTypeIngrediant = this.repository.findByNameAndType(ingredient.getName(), ingredient.getType());
        if(sameNameTypeIngrediant != null && sameNameTypeIngrediant.getId() != id){
            String message = "An ingredient with the same name and type already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        Optional<IngredientEntity> ingredientOpt = this.repository.findById(id);
        if (ingredientOpt.isEmpty()) {
            String message = NO_INGREDIENT_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        IngredientEntity updatedIngredient = this.toEntity(ingredient);
        updatedIngredient.setId(id);
        updatedIngredient = this.repository.save(updatedIngredient);
        log.info("Ingredient with id " + ingredient.getId() + " successfully updated");
        return this.toIngredient(updatedIngredient);
    }

    public void deleteIngredient(long id) throws NotFoundException {
        Optional<IngredientEntity> ingredientOpt = this.repository.findById(id);
        if (ingredientOpt.isEmpty()) {
            String message = NO_INGREDIENT_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        IngredientEntity entity = ingredientOpt.get();
        this.repository.delete(entity);
        log.info("Ingredient with id " + id + " successfully deleted");
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
