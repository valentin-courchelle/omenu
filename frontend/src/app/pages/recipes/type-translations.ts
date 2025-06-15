import { RecipeIngredientDto } from '../../generated';
import { IngredientDto } from '../../generated/model/ingredientDto';

export const typeTranslations: Record<IngredientDto.TypeEnum, string> = {
    VEGETABLE: 'Légume',
    MEAT: 'Viande',
    MILK_PRODUCT: 'Produit laitier',
    SPICE: 'Épice',
    SALT_GROCERY: 'Épicerie salée',
    SWEET_GROCERY: 'Épicerie sucrée',
    FRUIT: 'Fruit',
    CONDIMENT: 'Condiment'
};

export const unitTranslations: Record<RecipeIngredientDto.UnitEnum, string> = {
    KG: 'kg',
    G: 'g',
    L: 'L',
    ML: 'mL',
    PACKET: 'Sachet',
    NA: '',
    PINCH: 'Pincée',
    TABLESPOON: 'Cuillère à soupe',
    TEASPOON: 'Cuillère à café'
}
