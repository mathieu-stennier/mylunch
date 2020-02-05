package com.mstennie.rezdy.interview.mylunch.model.recipe.repository;

public class RecipeJsonResponse {
    private String title;
    private String[] ingredients;

    public RecipeJsonResponse() {}

    public RecipeJsonResponse(String title, String[] ingredients) {
        this.title = title;
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
