import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { typeColors } from '../type-color';
import { typeTranslations } from '../type-translations';


import {IngredientDto} from '../../../generated/model/ingredientDto'
import { IngredientService } from '../services/ingredient.service';


type IngredientColumn = 'name' | 'type' | 'recipeCount';

@Component({
  selector: 'app-ingredients-section',
  standalone: true,
  templateUrl: './ingredients-section.component.html',
  styleUrls: ['./ingredients-section.component.css'],
  imports: [FormsModule, CommonModule],
})
export class IngredientsSectionComponent implements OnInit {

  constructor(private ingredientService: IngredientService){}

  ingredients: IngredientDto[] = [];

  ingredientTypes: IngredientDto.TypeEnum[] = [];

  newIngredient : IngredientDto = {
    name: '',
    type: IngredientDto.TypeEnum.Vegetable,
    recipeCount: 0
  };

  

  sortedIngredients = [...this.ingredients];
  sortColumn: IngredientColumn = 'name'; // Utilisation du type ici
  sortDirection: 'asc' | 'desc' | '' = '';

  editedIngredientId: number | null = null;
  editedField: string = '';

  showPopup = false;
  ingredientToDelete: IngredientDto | undefined;

  hoveredRow: number = -1;


  ngOnInit(): void {
    this.ingredientService.getIngredients().subscribe({
      next: () => {
        this.ingredientService.ingredients$().subscribe({
          next: data => {
            console.log('Réponse reçue du backend :', data);
            console.log('Type de la donnée :', typeof data);
            this.ingredients = data; 
            this.applySort();       
          },
          error: err => console.log('Erreur lors du chargement des ingrédients', err),
        });
      },
      error: err => console.log('Erreur lors de l\'appel à getIngredients', err),
    });
  
    this.ingredientTypes = Object.values(IngredientDto.TypeEnum);
  }

  

  getBackgroundColor(type: IngredientDto.TypeEnum): string {
    return typeColors[type];
  }

  getTranslation(type: IngredientDto.TypeEnum): string {
    return typeTranslations[type] || type; 
  }

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
      if(a[column] === undefined){
        return -1;
      }
      if(b[column] === undefined){
        return -1;
      }
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
        ? 'sort-icon sort-asc' 
        : this.sortDirection === 'desc'
        ? 'sort-icon sort-desc'
        : '';
    }
    return '';
  }

  // Ajouter un ingrédient
  addIngredient() {
    if (this.newIngredient.name.trim()) {
      this.ingredientService.addIngredient(this.newIngredient).subscribe();
      this.newIngredient.name = ''; 
    } else {
      alert('Veuillez entrer un nom pour l\'ingrédient.');
    }
  }

  editRow(id: number, field: string){
    this.editedIngredientId = id;
    this.editedField = field;
  }

  saveEdit(){
    if(this.editedIngredientId){
      const editedIngredient = this.ingredients.find(i=>i.id === this.editedIngredientId);
      if(editedIngredient){
        this.ingredientService.updateIngredient(this.editedIngredientId,editedIngredient).subscribe();
      }
    }
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
    if(this.ingredientToDelete){
      this.ingredientService.deleteIngredient(this.ingredientToDelete.id!).subscribe()
      this.ingredientToDelete = undefined;
      this.showPopup = false;
      this.applySort();
    }
  }

  cancelDelete(){
    this.ingredientToDelete = undefined;
    this.showPopup = false;
    this.ingredientToDelete = undefined;
  }
}
