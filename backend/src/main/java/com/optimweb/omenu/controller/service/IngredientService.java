package com.optimweb.omenu.controller.service;

import com.optimweb.omenu.database.service.IngredientRepositoryService;
import com.optimweb.omenu.dto.IngredientDto;
import com.optimweb.omenu.exception.BadRequestException;
import com.optimweb.omenu.exception.NotFoundException;
import com.optimweb.omenu.model.Ingredient;
import com.optimweb.omenu.service.mapper.IngredientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

    public static final String NO_INGREDIENT_FOUND_WITH_ID = "No Ingredient found with id ";

    private final IngredientRepositoryService repositoryService;


    public List<IngredientDto> getAllIngredient() {
        return this.repositoryService.getAllIngredient().stream().map(IngredientMapper::toDto).collect(Collectors.toList());
    }

    public IngredientDto getIngredient(long id) throws NotFoundException {
        Ingredient ingredient = this.repositoryService.getIngredient(id);
        if (ingredient == null) {
            throw new NotFoundException(NO_INGREDIENT_FOUND_WITH_ID + id);
        }
        return IngredientMapper.toDto(ingredient);
    }

    public IngredientDto createIngredient(IngredientDto ingredientDto) throws BadRequestException {
        if (this.repositoryService.doesIngredientExist(ingredientDto.getName(), ingredientDto.getType())) {
            String message = "An ingredient with the same name and type already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        return IngredientMapper.toDto(this.repositoryService.saveIngredient(IngredientMapper.toIngredient(ingredientDto)));
    }

    public IngredientDto updateIngredient(long id, IngredientDto ingredientDto) throws BadRequestException, NotFoundException {
        if(repositoryService.doesIngredientExist(ingredientDto.getName(), ingredientDto.getType())){
            String message = "An ingredientDto with the same name and type already exists";
            log.error(message);
            throw new BadRequestException(message);
        }
        Ingredient ingredient = this.repositoryService.getIngredient(id);
        if (ingredient == null) {
            String message = NO_INGREDIENT_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        return IngredientMapper.toDto(this.repositoryService.updateIngredient(id, IngredientMapper.toIngredient(ingredientDto)));
    }

    public void deleteIngredient(long id) throws NotFoundException {
        Ingredient ingredient = this.repositoryService.getIngredient(id);
        if (ingredient == null) {
            String message = NO_INGREDIENT_FOUND_WITH_ID + id;
            log.error(message);
            throw new NotFoundException(message);
        }
        this.repositoryService.deleteIngredient(id);
        log.info("Ingredient with id {} successfully deleted", id);
    }
}
