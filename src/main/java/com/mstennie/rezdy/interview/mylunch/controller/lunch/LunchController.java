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

/**
 * LunchController will manage the /lunch endpoint
 *
 * This endpoint has the following features:
 *
 * ● GIVEN that I am an API client AND have made a ​GET​ request to the ​/lunch​ endpoint THEN I should receive a
 *      JSON response of the recipes that I can prepare based on the availability of the ingredients in my fridge
 * ● GIVEN that I am an API client AND I have made a ​GET​ request to the ​/lunch​ endpoint AND an ingredient is past
 *      its ​use-by​ date THEN I should not receive any recipes containing this ingredient
 * ● GIVEN that I am an API client AND I have made a ​GET​ request to the endpoint AND an ingredient is past its
 *     ​best-before​ date AND is still within its date THEN any recipe containing this ingredient should be sorted to the bottom of the JSON response object
 *
 */

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
