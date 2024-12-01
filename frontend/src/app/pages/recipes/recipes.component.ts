import { Component } from '@angular/core';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent {
  recipes = [
    { name: 'Spaghetti Bolognese' },
    { name: 'Poulet Curry' },
    { name: 'Salade César' }
  ];

  addRecipe() {
    const newRecipe = { name: 'Nouvelle Recette ' + (this.recipes.length + 1) };
    this.recipes.push(newRecipe);
  }
}
