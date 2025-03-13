import { Directive, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RecipeDto } from '../../../../generated';

@Directive()
export abstract class BaseRecipeComponent implements OnInit{
  @Input() recipe!: RecipeDto;
  
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

  abstract saveModification(): void;

  abstract cancelModification(): void;
}
