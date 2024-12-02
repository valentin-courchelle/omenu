import { Routes } from '@angular/router';
import { RecipesComponent } from './pages/recipes/recipes.component'; 
import { RecipesSectionComponent } from './pages/recipes/recipes-section/recipes-section.component';
import { IngredientsSectionComponent } from './pages/recipes/ingredients-section/ingredients-section.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // Redirection par défaut
  //{ path: 'home', loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent) },
  //{ path: 'menu', loadComponent: () => import('./pages/menu/menu.component').then(m => m.MenuComponent) },
  //{ path: 'profile', loadComponent: () => import('./pages/profile/profile.component').then(m => m.ProfileComponent) },
  {
    path: 'recipes',
    component: RecipesComponent, // La page Recipes inclut la sidebar
    children: [
      { path: '', redirectTo: 'recipes-list', pathMatch: 'full' },
      { path: 'recipes-list', component: RecipesSectionComponent }, 
      { path: 'ingredients-list', component: IngredientsSectionComponent }
    ],
  },
];
