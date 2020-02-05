package com.mstennie.rezdy.interview.mylunch.controller.lunch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import com.mstennie.rezdy.interview.mylunch.model.recipe.repository.RecipeJsonResponse;

import java.util.List;

public class RecipesForLunchJsonResponse {

    @JsonIgnore
    private List<Recipe> recipes;

    public RecipesForLunchJsonResponse(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @JsonProperty("recipes")
    public RecipeJsonResponse[] getRecipesJson(){
        return recipes.stream()
                .map(recipe -> new RecipeJsonResponse(recipe.getTitle(), recipe.getIngredients().stream().map(Ingredient::getName).toArray(String[]::new)))
                .toArray(RecipeJsonResponse[]::new);
    }
}
