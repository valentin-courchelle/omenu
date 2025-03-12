import { Component, Directive, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RecipeDto } from '../../../../generated';

@Directive()
export abstract class BaseRecipeComponent implements OnInit{
  @Input() recipe!: RecipeDto;
  @Input() isOpen!: boolean;
  @Input() isEditing!: boolean;
  
  form!: FormGroup;

  constructor(protected fb: FormBuilder) {}

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

  protected getEmptyForm(): FormGroup {
    return this.fb.group({
      name: [''],
      duration: [''],
      nbPeople: [''],
      rating: [''],
      description: [''],
    });
  }

  protected exitEditMode() {
    this.isEditing = false;
  };
  
  abstract toggleDetails(): void;

  abstract saveModification(): void;

  abstract cancelModification(): void;
}
