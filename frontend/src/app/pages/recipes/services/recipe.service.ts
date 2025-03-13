import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IngredientDto, RecipeControllerService, RecipeDto } from '../../../generated';
import { IngredientService } from './ingredient.service';


@Injectable({
  providedIn: 'root',
})
export class RecipeService {

  constructor(private recipeControllerService: RecipeControllerService, private ingredientService: IngredientService){};

  updateRecipe(id: number, recipe: RecipeDto): Observable<RecipeDto> {
    return this.recipeControllerService.updateRecipe(id, recipe);
  }

  searchIngredients(query: string): Observable<IngredientDto[]> {
    return this.ingredientService.searchIngredients(query);
  }
}
