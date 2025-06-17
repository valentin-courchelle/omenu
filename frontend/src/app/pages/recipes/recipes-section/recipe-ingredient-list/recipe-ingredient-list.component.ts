import { Component, OnInit, Input } from '@angular/core';
import { IngredientDto, RecipeDto, RecipeIngredientDto } from '../../../../generated';
import { RecipeService } from '../../services/recipe.service';
import { IngredientService } from '../../services/ingredient.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { unitTranslations } from '../../type-translations';
import { max } from 'rxjs';
import { typeColors } from '../../type-color';

@Component({
  selector: 'app-recipe-ingredient-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './recipe-ingredient-list.component.html',
  styleUrl: './recipe-ingredient-list.component.css'
})
export class RecipeIngredientListComponent implements OnInit {

  constructor(private recipeService: RecipeService, private ingredientService : IngredientService){}
  
  
  @Input() recipeIngredients!: RecipeIngredientDto[];
  @Input() editingMode!: boolean;

  unitEntries: RecipeIngredientDto.UnitEnum[] = [];

  newRecieIngredient: RecipeIngredientDto | null = null;
  allIngredients: IngredientDto[] = [];
  searchTerm: string = '';
  filteredIngredients: IngredientDto[] = [];
  selectedIndex = -1;
  selectedIngredient: IngredientDto | null = null;

  quantity: number | null = null;
  unit : RecipeIngredientDto.UnitEnum | null = null;
  
  ngOnInit(): void {
    // Get all ingredients from the service
    this.ingredientService.getIngredients().subscribe({
      next: () => {
        this.ingredientService.ingredients$().subscribe({
          next: data => {
            this.allIngredients = data;
          },
          error: err => console.error('Error fetching ingredients:', err)
        });
      }
    }
    );
    this.unitEntries = Object.values(RecipeIngredientDto.UnitEnum).sort((a,b)=> this.getTranslation(a).localeCompare(this.getTranslation(b)));

  }

  searchIngredients(): void {
    if(this.selectedIngredient && this.selectedIngredient.name !== this.searchTerm){
      this.selectedIngredient = null;
    }
    if(!this.searchTerm || this.searchTerm === ''){
      this.filteredIngredients = [];
    }
    // Filter the 5 first ingredients based on the search term order by name
    this.filteredIngredients = this.allIngredients.filter(ingredient =>
      ingredient.name.toLowerCase().includes(this.searchTerm.toLowerCase()) 
      && this.recipeIngredients.every(ri => ri.ingredientId !== ingredient.id)
    )
    .sort((a,b) => a.name.localeCompare(b.name))
    .slice(0, 5);

    if(this.filteredIngredients.length === 1 && this.filteredIngredients[0].name === this.searchTerm){
      this.selectedIngredient = this.filteredIngredients[0];
      this.filteredIngredients = [];
    }
    this.selectedIndex = -1;
  }

  selectIngredient(ingredient: IngredientDto): void {
    // Set the selected ingredient and reset the search term
    this.selectedIngredient = ingredient;
    this.searchTerm = this.selectedIngredient.name;
    this.filteredIngredients = [];
    this.selectedIndex = -1; 
  }

  onKeyDown(event: KeyboardEvent): void {
    const maxIndex = this.filteredIngredients.length - 1;
    switch (event.key) {
      case 'ArrowDown':
        if (this.selectedIndex < maxIndex) {
          this.selectedIndex++;
        }
        event.preventDefault();
        break;

      case 'ArrowUp':
        if (this.selectedIndex > 0) {
          this.selectedIndex--;
        }
        event.preventDefault();
        break;

      case 'Enter':
        if (this.selectedIndex >= 0 && this.selectedIndex <= maxIndex) {
          this.selectIngredient(this.filteredIngredients[this.selectedIndex]);
        }
        event.preventDefault();
        break;
    }
  }

  addIngredient(): void {
    if (this.selectedIngredient && this.quantity && this.unit) {
      // Create a new RecipeIngredientDto with the selected ingredient
      this.newRecieIngredient = {
        ingredientId: this.selectedIngredient.id,
        name: this.selectedIngredient.name,
        type: this.selectedIngredient.type,
        quantity: this.quantity,
        unit: this.unit 
      };
      this.recipeIngredients.push(this.newRecieIngredient);
      this.newRecieIngredient = null;
      this.clearIngredientForm();
    }
  }

  removeIngredient(id: number | undefined): void {
    // Remove the ingredient by its id
    const index = this.recipeIngredients.findIndex(ingredient => ingredient.ingredientId === id);
    if (index === -1) {
      console.warn(`Ingredient with id ${id} not found.`);
      return;
    } 
    this.recipeIngredients.splice(index, 1);
  }

  getTranslation(type: RecipeIngredientDto.UnitEnum | undefined): string {
      return type ? unitTranslations[type] : ''; 
  }

  clearIngredientForm(): void{
    this.searchTerm ='';
    this.selectedIngredient = null;
    this.quantity = null;
    this.unit = null;
    this.newRecieIngredient = null;
    this.filteredIngredients = [];
  }

  canAddIngredient(): boolean {
    return !!this.selectedIngredient && !!this.quantity && !!this.unit;
  }

  getColor(type: IngredientDto.TypeEnum | undefined): string {
    return type ? typeColors[type] || '#f9f9f9' : '#f9f9f9';
  }
  

}
