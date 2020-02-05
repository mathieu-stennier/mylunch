package com.mstennie.rezdy.interview.mylunch.model.ingredient.repository;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;

import java.util.List;

/**
 * IngredientRepository represents an entity that holds a set of ingredients.
 */
public interface IngredientRepository {

    /**
     * Returns all available ingredient instances in that repository.
     */
    List<IngredientInstance> getAvailableIngredients();
}
