package com.mstennie.rezdy.interview.mylunch.model.recipe.service;

public enum RecipeIngredientMatchingStrategy {
    ALL,            // Return all recipes that matches the available ingredients.
    NOT_EXPIRED,    // Return all recipes that matches the available ingredients that are not expired.
    FRESH           // Return all recipes that matches the available ingredients that are not expired and not passed the best-before date.
}
