import { Component, Input, OnInit } from '@angular/core';
import { RecipeDto } from '../../../../generated';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BaseRecipeComponent } from '../base-recipe/base-recipe.component';

@Component({
  selector: 'app-recipe',
  standalone: true,
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class RecipeComponent extends BaseRecipeComponent  {

  constructor(protected override fb: FormBuilder) {
    super(fb);
  }

  override toggleDetails() {
    this.isOpen = !this.isOpen;
  }

  toggleEditionMode() {
    this.form.patchValue(this.recipe); 
    this.isEditing = true;
  }

  override saveModification() {
    Object.assign(this.recipe, this.form.value);
    // TODO: call backend save recipe endpoint
    this.exitEditMode();
  }

  override cancelModification() {
    this.form.patchValue(this.recipe);
    this.exitEditMode();
  }
}
