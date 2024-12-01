import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipesSectionComponent } from './recipes-section.component';

describe('RecipesSectionComponent', () => {
  let component: RecipesSectionComponent;
  let fixture: ComponentFixture<RecipesSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecipesSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipesSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
