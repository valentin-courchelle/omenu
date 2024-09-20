package com.optimweb.omenu.controller.model;

import com.optimweb.omenu.model.MealTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCreationRequest {

    private Map<Date, List<MealTime>> mealTimeByDate;


}
