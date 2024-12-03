package com.optimweb.omenu.dto;

import com.optimweb.omenu.model.MealTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealDto {

    private MealTime time;

    private RecipeDto recipe;
}
