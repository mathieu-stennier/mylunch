package com.mstennie.rezdy.interview.mylunch.model.recipe.service;

public enum RecipeIngredientSortingStrategy {
    NONE,           // No sorting will be performed.
    FRESHEST        // The recipes with the freshest ingredients will show up first in the list. Freshest = all ingredients are before best-before date.
}
