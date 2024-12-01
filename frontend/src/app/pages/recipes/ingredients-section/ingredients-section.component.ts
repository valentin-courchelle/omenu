import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ingredients-section',
  standalone: true,
  templateUrl: './ingredients-section.component.html',
  styleUrls: ['./ingredients-section.component.css'],
  imports: [FormsModule, CommonModule]
})
export class IngredientsSectionComponent {
  ingredients = [
    { name: 'Tomate' },
    { name: 'Poulet' },
    { name: 'Lait' }
  ];

  searchTerm = '';
  filteredIngredients = this.ingredients;

  searchIngredients() {
    this.filteredIngredients = this.ingredients.filter(ingredient =>
      ingredient.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addIngredient() {
    const newIngredient = { name: 'Nouvel Ingrédient ' + (this.ingredients.length + 1) };
    this.ingredients.push(newIngredient);
    this.searchIngredients(); // Actualise la liste filtrée
  }
}
