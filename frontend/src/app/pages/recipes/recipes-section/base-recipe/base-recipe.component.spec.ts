import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseRecipeComponent } from './base-recipe.component';

describe('BaseRecipeComponent', () => {
  let component: BaseRecipeComponent;
  let fixture: ComponentFixture<BaseRecipeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BaseRecipeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BaseRecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
