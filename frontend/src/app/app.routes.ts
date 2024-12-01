import { Routes } from '@angular/router';
import { RecipesComponent } from './pages/recipes/recipes.component';
import { RecipesSectionComponent } from './pages/recipes/recipes-section/recipes-section.component';
import { IngredientsSectionComponent } from './pages/recipes/ingredients-section/ingredients-section.component';

export const routes: Routes = [
  { path: '', redirectTo: '/recipes', pathMatch: 'full' }, // Default redirection
  { path: 'recipes', component: RecipesSectionComponent },
  { path: 'ingredients', component: IngredientsSectionComponent },
  { path: 'menu', component: RecipesComponent }, // Placeholder
  { path: 'profile', component: RecipesComponent }, // Placeholder
];
