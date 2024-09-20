package com.optimweb.omenu.controller;

import com.optimweb.omenu.controller.model.MenuCreationRequest;
import com.optimweb.omenu.model.Ingredient;
import com.optimweb.omenu.model.Menu;
import com.optimweb.omenu.model.RecipeIngredient;
import com.optimweb.omenu.servie.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody MenuCreationRequest request){
        Menu menu = this.menuService.generateMenu(request.getMealTimeByDate());
        return ResponseEntity.ok().body(menu);
    }

    @PostMapping("/ingredients")
    public ResponseEntity<List<RecipeIngredient>> createIngredientList(@RequestBody Menu menu){
        return ResponseEntity.ok().body(this.menuService.generateIngredientList(menu));
    }
}
