import { Component, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { BaseRecipeComponent } from '../base-recipe/base-recipe.component';
import { RecipeDto } from '../../../../generated';
import { CommonModule } from '@angular/common';
import { AutoResizeTextareaDirective } from '../../../../directives/auto-resize-textarea.directive';
import { RecipeIngredientListComponent } from '../recipe-ingredient-list/recipe-ingredient-list.component';

@Component({
  selector: 'app-new-recipe',
  standalone: true,
  templateUrl: './new-recipe.component.html',
  styleUrls: ['./new-recipe.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, AutoResizeTextareaDirective, RecipeIngredientListComponent]
  
})
export class NewRecipeComponent extends BaseRecipeComponent {
  
  @Output() confirmNewRecipe = new EventEmitter<RecipeDto>();
  @Output() closeNewRecipe = new EventEmitter<void>();

  constructor(protected override fb: FormBuilder) {
    super(fb);
    this.form = this.getEmptyForm();
  }

  override saveModification(): void {
    Object.assign(this.recipe, this.form.value);
    this.confirmNewRecipe.emit(this.recipe);
    this.closeNewRecipe.emit();
  }
  override cancelModification(): void {
    this.form = this.getEmptyForm();
    this.closeNewRecipe.emit();
  }
}
