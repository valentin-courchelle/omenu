import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { RecipeDto } from '../../../generated/model/recipeDto';
import { RecipeControllerService } from '../../../generated/api/recipeController.service';
import { switchMap, tap } from 'rxjs/operators';
import { parseBlobToObservableJson } from '../../../utils';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private recipesCache = new BehaviorSubject<RecipeDto[]>([]);
  
  constructor(private apiService: RecipeControllerService) {}
  
  recipes$(): Observable<RecipeDto[]> {
      return this.recipesCache.asObservable();
    }

    getRecipes(): Observable<RecipeDto[]> {
      return this.apiService.getAllRecipe().pipe(
        switchMap(response => {
          if (response instanceof Blob) {
            return parseBlobToObservableJson<RecipeDto[]>(response);
          }
          return [response];
        }),
        tap(ingredients => this.recipesCache.next(ingredients))
      );
    }

  addRecipe(recipe: RecipeDto): Observable<RecipeDto> {
      return this.apiService.createRecipe(recipe ).pipe(
        tap(response => {
          if(response instanceof Blob){
            parseBlobToObservableJson<RecipeDto>(response).subscribe(recipe => {
              const updatedCache = [...this.recipesCache.value, recipe];
              this.recipesCache.next(updatedCache);
            });
          } else{
            const updatedCache = [...this.recipesCache.value, response];
            this.recipesCache.next(updatedCache);
          }
        })
      );
    }
  
    
    updateRecipe(id: number, recipe: RecipeDto): Observable<RecipeDto> {
      return this.apiService.updateRecipe(id, recipe ).pipe(
        tap(updatedRecipe => {
          const updatedCache = this.recipesCache.value.map(ing =>
            ing.id === updatedRecipe.id ? updatedRecipe : ing
          );
          this.recipesCache.next(updatedCache);
        })
      );
    }
  
    deleteRecipe(id: number): Observable<void> {
      return this.apiService.deleteRecipe(id).pipe(
        tap(() => {
          const updatedCache = this.recipesCache.value.filter(ing => ing.id !== id);
          this.recipesCache.next(updatedCache);
        })
      );
    }

}