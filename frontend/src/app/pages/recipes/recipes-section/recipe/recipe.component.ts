import { Component, Input, OnInit } from '@angular/core';
import { RecipeDto } from '../../../../generated';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recipe',
  standalone: true,
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class RecipeComponent implements OnInit {
  @Input() recipe!: RecipeDto;
  form!: FormGroup;
  isEditing = false;
  isOpen = false;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.form = this.fb.group({
      name: [this.recipe?.name || ''],
      duration: [this.recipe?.duration || ''],
      nbPeople: [this.recipe?.nbPeople || ''],
      rating: [this.recipe?.rating || ''],
      description: [this.recipe?.description || ''],
    });
  }

  toggleDetails() {
    this.isOpen = !this.isOpen;
  }

  toggleEditionMode() {
    this.form.patchValue(this.recipe); 
    this.isEditing = true;
  }

  saveModification() {
    Object.assign(this.recipe, this.form.value);
    // TODO: call backend save recipe endpoint
    this.exitEditMode();
  }

  cancelModification() {
    this.form.patchValue(this.recipe);
    this.exitEditMode();
  }

  exitEditMode() {
    this.isEditing = false;
  }
}
