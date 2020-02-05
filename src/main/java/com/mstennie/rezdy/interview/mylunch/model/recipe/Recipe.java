package com.mstennie.rezdy.interview.mylunch.model.recipe;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Recipe {
    private String title;
    private Set<Ingredient> ingredients;

    public Recipe(String title, Collection<Ingredient> ingredients) {
        this.title = title;
        this.ingredients = new HashSet<>(ingredients);
    }

    public Recipe(String title, Ingredient... ingredients) {
        this.title = title;
        this.ingredients = new HashSet<>();
        this.ingredients.addAll(Arrays.asList(ingredients));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(title, recipe.title) &&
                Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, ingredients);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
