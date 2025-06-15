import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BaseRecipeComponent } from '../base-recipe/base-recipe.component';
import { RecipeService } from '../../services/recipe.service';
import { RecipeIngredientListComponent } from './recipe-ingredient-list/recipe-ingredient-list.component';

@Component({
  selector: 'app-recipe',
  standalone: true,
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, RecipeIngredientListComponent]
})
export class RecipeComponent extends BaseRecipeComponent  {

  isOpen = false;
  isEditing = false;

  constructor(protected override fb: FormBuilder, private recipeService: RecipeService) {
    super(fb);
  }

  toggleDetails() {
    this.isOpen = !this.isOpen;
  }

  toggleEditionMode() {
    this.form.patchValue(this.recipe); 
    this.isEditing = true;
  }

  override saveModification() {
    Object.assign(this.recipe, this.form.value);
    if(this.recipe.id) {
      this.recipeService.updateRecipe(this.recipe.id, this.recipe).subscribe();
    }
    this.exitEditMode();
  }

  override cancelModification() {
    this.form.patchValue(this.recipe);
    this.exitEditMode();
  }

  protected exitEditMode() {
    this.isEditing = false;
  };
}
