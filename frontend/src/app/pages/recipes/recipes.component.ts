import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from './sidebar/sidebar.component';

@Component({
  selector: 'app-recipes',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent],
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
