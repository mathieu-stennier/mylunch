package com.mstennie.rezdy.interview.mylunch.model.recipe.repository;

import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;

import java.util.List;

public interface RecipeRepository {
    /**
     * Returns all recipes in the repo.
     */
    List<Recipe> getAllRecipes();
}
