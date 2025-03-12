import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  private apiUrl = 'https://api.monbackend.com';

  constructor(private http: HttpClient) {}

  searchIngredients(query: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/ingredients/search?q=${query}`);
  }
}
