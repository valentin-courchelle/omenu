import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-recipes-section',
  standalone: true,
  templateUrl: './recipes-section.component.html',
  styleUrls: ['./recipes-section.component.css'],
  imports: [FormsModule, CommonModule]
})
export class RecipesSectionComponent {
  recipes = [
    { name: 'Spaghetti Bolognese' },
    { name: 'Poulet Curry' },
    { name: 'Salade César' }
  ];

  searchTerm = '';
  filteredRecipes = this.recipes;

  searchRecipes() {
    this.filteredRecipes = this.recipes.filter(recipe =>
      recipe.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addRecipe() {
    const newRecipe = { name: 'Nouvelle Recette ' + (this.recipes.length + 1) };
    this.recipes.push(newRecipe);
    this.searchRecipes(); // Actualise la liste filtrée
  }
}
