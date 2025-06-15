import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeIngredientListComponent } from './recipe-ingredient-list.component';

describe('RecipeIngredientListComponent', () => {
  let component: RecipeIngredientListComponent;
  let fixture: ComponentFixture<RecipeIngredientListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecipeIngredientListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipeIngredientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
