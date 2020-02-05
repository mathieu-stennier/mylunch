package com.mstennie.rezdy.interview.mylunch.model.ingredient.repository;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;

import java.util.List;

public interface IngredientRepository {
    List<IngredientInstance> getAvailableIngredients();
}
