import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { max } from 'rxjs';

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
    { id: 1, name: 'Carotte', type: 'Légume', recipeCount: 5 },
    { id: 2, name: 'Poulet', type: 'Viande', recipeCount: 10 },
    { id: 3, name: 'Saumon', type: 'Poisson', recipeCount: 3 },
    { id: 4, name: 'Emmental', type: 'Produit-laitier', recipeCount: 8 },
    { id: 5, name: 'Camembert', type: 'Produit-laitier', recipeCount: 1 },
    { id: 6, name: 'Courgettes', type: 'Légume', recipeCount: 2 },
    { id: 7, name: 'Tomates', type: 'Légume', recipeCount: 5 },
    { id: 8, name: 'Saumon', type: 'Poisson', recipeCount: 3 },
    { id: 9, name: 'Navet', type: 'Légume', recipeCount: 9 },
    { id: 10, name: 'Porc', type: 'Viande', recipeCount: 7 },
    { id: 11, name: 'Colin', type: 'Poisson', recipeCount: 2 },
    { id: 12, name: 'Brocoli', type: 'Légume', recipeCount: 0 },
    { id: 13, name: 'Crème fraiche', type: 'Produit-laitier', recipeCount: 8 }
  ];

  ingredientTypes = ['Légume', 'Viande', 'Poisson', 'Produit-laitier'];

  newIngredient = {
    name: '',
    type: 'Légume',
  };

  sortedIngredients = [...this.ingredients];
  sortColumn: IngredientColumn = 'name'; // Utilisation du type ici
  sortDirection: 'asc' | 'desc' | '' = '';

  editedIngredientId: number | null = null;
  editedField: string = '';

  showPopup = false;
  ingredientToDelete: {id: number, name:string, type:string, recipeCount: number} | undefined;

  hoveredRow: number = -1;


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

  generateId(): number {
    return this.ingredients.length > 0
      ? Math.max(...this.ingredients.map((ingredient) => ingredient.id)) + 1
      : 1;
  }

  // Ajouter un ingrédient
  addIngredient() {
    if (this.newIngredient.name.trim()) {
      const maxId = this.ingredients.length > 0 ? Math.max(...this.ingredients.map(ingredient => ingredient.id)) : 1;
      this.ingredients.push({
        id: this.generateId(),
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

  editRow(id: number, field: string){
    this.editedIngredientId = id;
    this.editedField = field;
  }

  saveEdit(){
    this.editedIngredientId = null;
    this.editedField = '';
    this.applySort();
  }

  deleteIngredient(id: number){
    const ingredient = this.ingredients.find(i=>i.id === id);
    if(!ingredient){
      return;
    }

    this.ingredientToDelete = ingredient;
    if(ingredient.recipeCount > 0){
      this.showPopup = true;
    } else{
      this.confirmDelete();
    }
  }

  confirmDelete(){
    this.ingredients = this.ingredients.filter(i=>i.id !== this.ingredientToDelete?.id);
    this.ingredientToDelete = undefined;
    this.showPopup = false;
    this.applySort();
  }

  cancelDelete(){
    this.ingredientToDelete = undefined;
  }
}
