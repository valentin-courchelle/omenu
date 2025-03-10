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
