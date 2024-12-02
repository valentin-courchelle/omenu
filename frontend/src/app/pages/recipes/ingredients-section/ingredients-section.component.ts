import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

// Type pour les colonnes triables
type IngredientColumn = 'name' | 'type' | 'recipeCount';

@Component({
  selector: 'app-ingredients-section',
  standalone: true,
  templateUrl: './ingredients-section.component.html',
  styleUrls: ['./ingredients-section.component.css'],
  imports: [FormsModule, CommonModule]
})
export class IngredientsSectionComponent {
  ingredients = [
    { name: 'Carotte', type: 'Légume', recipeCount: 5 },
    { name: 'Poulet', type: 'Viande', recipeCount: 10 },
    { name: 'Saumon', type: 'Poisson', recipeCount: 3 },
    { name: 'Emmental', type: 'Produit-laitier', recipeCount: 8 },
    { name: 'Camembert', type: 'Produit-laitier', recipeCount: 1 },
    { name: 'Courgettes', type: 'Légume', recipeCount: 2 },
    { name: 'Tomates', type: 'Légume', recipeCount: 5 },
    { name: 'Saumon', type: 'Poisson', recipeCount: 3 },
    { name: 'Navet', type: 'Légume', recipeCount: 9 },
    { name: 'Porc', type: 'Viande', recipeCount: 7 },
    { name: 'Colin', type: 'Poisson', recipeCount: 2 },
    { name: 'Brocoli', type: 'Légume', recipeCount: 1 },
    { name: 'Crème fraiche', type: 'Produit-laitier', recipeCount: 8 }
  ];

  ingredientTypes = ['Légume', 'Viande', 'Poisson', 'Produit-laitier'];

  newIngredient = {
    name: '',
    type: 'Légume',
  };

  sortedIngredients = [...this.ingredients];
  sortColumn: IngredientColumn = 'name'; // Utilisation du type ici
  sortDirection: 'asc' | 'desc' | '' = '';

  // Méthode pour trier par colonne
  sortBy(column: IngredientColumn) {
    if (this.sortColumn === column) {
      this.sortDirection =
        this.sortDirection === 'asc' ? 'desc' : this.sortDirection === 'desc' ? '' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
    this.applySort();
  }

  applySort() {
    if (this.sortDirection === '') {
      this.sortedIngredients = [...this.ingredients];
      return;
    }

    const directionMultiplier = this.sortDirection === 'asc' ? 1 : -1;

    this.sortedIngredients = [...this.ingredients].sort((a, b) => {
      const column = this.sortColumn as keyof typeof a;
      if (a[column] < b[column]) {
        return -1 * directionMultiplier;
      }
      if (a[column] > b[column]) {
        return 1 * directionMultiplier;
      }
      return 0;
    });
  }

  getSortClass(column: string): string {
    if (this.sortColumn === column) {
      return this.sortDirection === 'asc'
        ? 'sort-icon sort-asc' // Flèche vers le bas
        : this.sortDirection === 'desc'
        ? 'sort-icon sort-desc' // Flèche vers le haut
        : '';
    }
    return '';
  }

  // Ajouter un ingrédient
  addIngredient() {
    if (this.newIngredient.name.trim()) {
      this.ingredients.push({
        name: this.newIngredient.name.trim(),
        type: this.newIngredient.type,
        recipeCount: 0,
      });
      this.newIngredient.name = ''; // Reset input
      this.applySort(); // Réapplique le tri après ajout
    } else {
      alert('Veuillez entrer un nom pour l\'ingrédient.');
    }
  }
}
