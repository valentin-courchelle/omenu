import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { typeColors } from '../type-color';
import { typeTranslations } from '../type-translations';


import {RecipeDto} from '../../../generated/model/recipeDto'
import { RecipesService } from './recipes-section.service';
import { IngredientDto } from '../../../generated/model/ingredientDto';
import { RecipeComponent } from './recipe/recipe.component';

@Component({
  selector: 'app-recipes-section',
  standalone: true,
  templateUrl: './recipes-section.component.html',
  styleUrls: ['./recipes-section.component.css'],
  imports: [FormsModule, CommonModule, RecipeComponent]
})
export class RecipesSectionComponent implements OnInit{
  
  constructor(private recipesService: RecipesService){}
  
  recipes: RecipeDto[] = [];
  editingRecipeId: number | undefined = undefined;

  
  searchTerm = '';
  filteredRecipes: RecipeDto[] = [];
  
  ngOnInit(): void {
    this.recipesService.getRecipes().subscribe({
          next: () => {
            this.recipesService.recipes$().subscribe({
              next: data => {
                this.recipes = data; 
                this.filteredRecipes = this.recipes;
              },
              error: err => console.log('Erreur lors du chargement des recettes', err),
            });
          },
          error: err => console.log('Erreur lors de l\'appel à getRecipes', err),
        });      
  }


  searchRecipes() {
    this.filteredRecipes = this.recipes.filter(recipe =>
      recipe.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addRecipe() {
    const newRecipe : RecipeDto = {
      name: 'Nouvelle Recette ' + (this.recipes.length + 1),
      duration: 0,
      nbPeople: 0,
      rating: 0,
      season: [],
      ingredients: []
    };
    this.recipes.push(newRecipe);
    this.searchRecipes();
  }

  updateRecipe(updatedRecipe: RecipeDto) {
    const index = this.filteredRecipes.findIndex(r => r.id === updatedRecipe.id);
    if (index !== -1) {
      this.filteredRecipes[index] = updatedRecipe;
    }
    this.editingRecipeId = undefined;
  }

  getBackgroundColor(type: IngredientDto.TypeEnum): string {
    return typeColors[type];
  }

  getTranslation(type: IngredientDto.TypeEnum): string {
    return typeTranslations[type] || type;
  }
}
