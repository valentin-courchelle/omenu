import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { typeColors } from '../type-color';
import { typeTranslations } from '../type-translations';


import {RecipeDto} from '../../../generated/model/recipeDto'
import { RecipesSectionService } from '../services/recipes-section.service';
import { IngredientDto } from '../../../generated/model/ingredientDto';
import { RecipeComponent } from './recipe/recipe.component';
import { NewRecipeComponent } from './new-recipe/new-recipe.component';

@Component({
  selector: 'app-recipes-section',
  standalone: true,
  templateUrl: './recipes-section.component.html',
  styleUrls: ['./recipes-section.component.css'],
  imports: [FormsModule, CommonModule, RecipeComponent, NewRecipeComponent]
})
export class RecipesSectionComponent implements OnInit{
  
  constructor(private recipesSectionService: RecipesSectionService){}
  
  recipes: RecipeDto[] = [];
  showNewRecipe = false;
  
  searchTerm = '';
  filteredRecipes: RecipeDto[] = [];
  
  ngOnInit(): void {
    this.recipesSectionService.getRecipes().subscribe({
          next: () => {
            this.recipesSectionService.recipes$().subscribe({
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

  openNewRecipe() {
    this.showNewRecipe = true;
  }

  saveNewRecipe(newRecipe : RecipeDto){
    this.recipesSectionService.addRecipe(newRecipe).subscribe({
      next: () => {
        this.recipesSectionService.getRecipes().subscribe({
          next: () => {
            this.recipesSectionService.recipes$().subscribe({
              next: data => {
                this.recipes = data; 
                this.filteredRecipes = this.recipes;
                this.showNewRecipe = false;
              },
              error: err => console.log('Erreur lors du chargement des recettes', err),
            });
          },
          error: err => console.log('Erreur lors de l\'appel à getRecipes', err),
        });
      },
      error: err => console.log('Erreur lors de l\'ajout de la recette', err),
    });
  }

  getBackgroundColor(type: IngredientDto.TypeEnum): string {
    return typeColors[type];
  }

  getTranslation(type: IngredientDto.TypeEnum): string {
    return typeTranslations[type] || type;
  }
}
