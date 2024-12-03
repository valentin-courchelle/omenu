export * from './ingredientController.service';
import { IngredientControllerService } from './ingredientController.service';
export * from './menuController.service';
import { MenuControllerService } from './menuController.service';
export * from './recipeController.service';
import { RecipeControllerService } from './recipeController.service';
export const APIS = [IngredientControllerService, MenuControllerService, RecipeControllerService];
