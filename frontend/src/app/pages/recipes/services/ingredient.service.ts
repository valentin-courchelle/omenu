import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { IngredientDto } from '../../../generated/model/ingredientDto';
import { IngredientControllerService } from '../../../generated/api/ingredientController.service';
import { switchMap, tap, map } from 'rxjs/operators';
import { parseBlobToObservableJson } from '../../../utils';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  private ingredientsCache = new BehaviorSubject<IngredientDto[]>([]);

  constructor(private apiService: IngredientControllerService) {}

  getIngredients(): Observable<IngredientDto[]> {
    return this.apiService.getAllIngredient().pipe(
      switchMap(response => {
        if (response instanceof Blob) {
          return parseBlobToObservableJson<IngredientDto[]>(response);
        }
        return [response];
      }),
      tap(ingredients => this.ingredientsCache.next(ingredients))
    );
  }

  searchIngredients(query: string): Observable<IngredientDto[]> {
    if(this.ingredientsCache.value.length === 0){
      return this.getIngredients().pipe(
        map(ingredients => {
          return ingredients.filter(ingredient => ingredient.name.toLowerCase().includes(query.toLowerCase()));
        })
      );
    }
    return this.ingredientsCache.pipe(
      map(ingredients => {
        return ingredients.filter(ingredient => ingredient.name.toLowerCase().includes(query.toLowerCase()));
      })
    );
  }

  
  ingredients$(): Observable<IngredientDto[]> {
    return this.ingredientsCache.asObservable();
  }

  
  addIngredient(ingredient: IngredientDto): Observable<IngredientDto> {
    return this.apiService.createIngredient(ingredient ).pipe(
      tap(response => {
        if(response instanceof Blob){
          parseBlobToObservableJson<IngredientDto>(response).subscribe(ingredient => {
            const updatedCache = [...this.ingredientsCache.value, ingredient];
            this.ingredientsCache.next(updatedCache);
          });
        } else{
          const updatedCache = [...this.ingredientsCache.value, response];
          this.ingredientsCache.next(updatedCache);
        }
      })
    );
  }

  getCachedIngredients(): IngredientDto[] {
    return this.ingredientsCache.value;
  }

  
  updateIngredient(id: number, ingredient: IngredientDto): Observable<IngredientDto> {
    return this.apiService.updateIngredient(id, ingredient ).pipe(
      tap(updatedIngredient => {
        const updatedCache = this.ingredientsCache.value.map(ing =>
          ing.id === updatedIngredient.id ? updatedIngredient : ing
        );
        this.ingredientsCache.next(updatedCache);
      })
    );
  }

  // Supprimer un ingrédient
  deleteIngredient(id: number): Observable<void> {
    return this.apiService.deleteIngredient(id).pipe(
      tap(() => {
        const updatedCache = this.ingredientsCache.value.filter(ing => ing.id !== id);
        this.ingredientsCache.next(updatedCache);
      })
    );
  }
}
