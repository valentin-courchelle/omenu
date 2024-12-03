import { IngredientDto } from '../../../generated/model/ingredientDto';

export const typeColors: Record<IngredientDto.TypeEnum, string> = {
    VEGETABLE: '#8BC34A',
    MEAT: '#D32F2F',
    MILK_PRODUCT: '#f5f5dc',
    SPICE: '#FF5722',
    SALT_GROCERY: '#D7CCC8',
    SWEET_GROCERY: '#FFB6C1',
    FRUIT: '#FF9800',
    CONDIMENT: '#FFEB3B'
};
