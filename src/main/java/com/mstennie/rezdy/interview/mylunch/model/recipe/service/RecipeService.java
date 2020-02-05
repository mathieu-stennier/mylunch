package com.mstennie.rezdy.interview.mylunch.model.recipe.service;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;

import java.util.List;

/**
 * The RecipeService provides all sorts of methods around dealing with Recipes.
 */
public interface RecipeService {

    /**
     * Returns all recipes in the repo.
     */
    List<Recipe> getAllRecipes();

    /**
     * Return all the recipes that matches the given IngredientInstance list according to the
     * matching strategy. The returned list is sorted according to the matching and sorting strategy.
     * @see RecipeIngredientMatchingStrategy for more information on matching strategies.
     * @see RecipeIngredientSortingStrategy for more information on sorting strategies.
     */
    List<Recipe> getMatchingRecipesForIngredients(List<IngredientInstance> ingredients, RecipeIngredientMatchingStrategy matchingStrategy, RecipeIngredientSortingStrategy sortingStrategy);

    /**
     * Return all the recipes that matches the given IngredientInstance list according to the
     * matching strategy. The returned list is sorted according to the matching strategy.
     * The recipes won't be sorted.
     * @see RecipeIngredientMatchingStrategy for more information on matching strategies.
     */
    List<Recipe> getMatchingRecipesForIngredients(List<IngredientInstance> ingredients, RecipeIngredientMatchingStrategy matchingStrategy);
}
