package com.mstennie.rezdy.interview.mylunch.model.recipe.repository;

import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;

import java.util.List;

/**
 * Represents a repository of recipes.
 */
public interface RecipeRepository {
    /**
     * Returns all recipes in the repo.
     * @throws RepositoryNotAvailableException when the repository is not accessible at the moment.
     */
    List<Recipe> getAllRecipes();
}
