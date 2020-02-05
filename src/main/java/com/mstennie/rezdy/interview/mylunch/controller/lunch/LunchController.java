package com.mstennie.rezdy.interview.mylunch.controller.lunch;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.repository.IngredientRepository;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import com.mstennie.rezdy.interview.mylunch.model.recipe.service.RecipeIngredientMatchingStrategy;
import com.mstennie.rezdy.interview.mylunch.model.recipe.service.RecipeIngredientSortingStrategy;
import com.mstennie.rezdy.interview.mylunch.model.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LunchController {

    final IngredientRepository ingredientRepository;
    final RecipeService recipeService;

    @Autowired
    public LunchController(IngredientRepository ingredientRepository, RecipeService recipeService) {
        this.ingredientRepository = ingredientRepository;
        this.recipeService = recipeService;
    }

    @GetMapping("/lunch")
    public RecipesForLunchJsonResponse lunch() {
        List<IngredientInstance> allIngredients = ingredientRepository.getAvailableIngredients();
        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(allIngredients, RecipeIngredientMatchingStrategy.NOT_EXPIRED, RecipeIngredientSortingStrategy.FRESHEST);
        return new RecipesForLunchJsonResponse(matchingRecipesForIngredients);
    }
}
