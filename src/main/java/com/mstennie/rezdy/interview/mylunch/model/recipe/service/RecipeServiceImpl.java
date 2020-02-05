package com.mstennie.rezdy.interview.mylunch.model.recipe.service;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import com.mstennie.rezdy.interview.mylunch.model.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.getAllRecipes();
    }

    @Override
    public List<Recipe> getMatchingRecipesForIngredients(List<IngredientInstance> ingredients, RecipeIngredientMatchingStrategy matchingStrategy, RecipeIngredientSortingStrategy sortingStrategy) {
        // Get all the recipes
        List<Recipe> allRecipes = getAllRecipes();

        // Get available ingredients based on matching strategy
        Set<Ingredient> ingredientsAvailable = null;
        switch (matchingStrategy) {
            case ALL:
                ingredientsAvailable = ingredients.stream().map(IngredientInstance::getIngredient).collect(Collectors.toSet());
                break;
            case FRESH:
                ingredientsAvailable = ingredients.stream().filter(ingredient -> ingredient.getBestBefore().isAfter(LocalDate.now())).map(IngredientInstance::getIngredient).collect(Collectors.toSet());
                break;
            case NOT_EXPIRED:
                ingredientsAvailable = ingredients.stream().filter(ingredient -> ingredient.getUseBefore().isAfter(LocalDate.now())).map(IngredientInstance::getIngredient).collect(Collectors.toSet());
                break;
            default:
                throw new RuntimeException(String.format("%s matching strategy is not supported.", matchingStrategy.toString()));
        }

        final Set<Ingredient> finalIngredientsAvailable = ingredientsAvailable;

        // Process with filtering
        Stream<Recipe> stream = allRecipes.stream().filter(recipe -> finalIngredientsAvailable.containsAll(recipe.getIngredients()));

        // Process with sorting
        switch (sortingStrategy) {
            case NONE:
                // Nothing to do
                break;
            case FRESHEST:
                Set<Ingredient> ingredientsPastBestBeforeDate = ingredients.stream().filter(ingredient -> ingredient.getBestBefore().isBefore(LocalDate.now())).map(IngredientInstance::getIngredient).collect(Collectors.toSet());
                stream = stream.sorted(new RecipeIngredientFreshComparator(ingredientsPastBestBeforeDate));
                break;
            default:
                throw new RuntimeException(String.format("%s sorting strategy is not supported.", sortingStrategy.toString()));
        }

        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getMatchingRecipesForIngredients(List<IngredientInstance> ingredients, RecipeIngredientMatchingStrategy matchingStrategy) {
        return getMatchingRecipesForIngredients(ingredients, matchingStrategy, RecipeIngredientSortingStrategy.NONE);
    }

    private static class RecipeIngredientFreshComparator implements Comparator<Recipe> {

        Set<Ingredient> ingredientsThatPastBestBeforeDates;

        public RecipeIngredientFreshComparator(Collection<Ingredient> ingredientsThatPastBestBeforeDates) {
            this.ingredientsThatPastBestBeforeDates = new HashSet<>(ingredientsThatPastBestBeforeDates);
        }

        @Override
        public int compare(Recipe recipe1, Recipe recipe2) {
            boolean recipe1ContainsIngredientsThatArePastBestBeforeDates = recipeContainsIngredientsThatArePastBestBeforeDates(recipe1);
            boolean recipe2ContainsIngredientsThatArePastBestBeforeDates = recipeContainsIngredientsThatArePastBestBeforeDates(recipe2);
            if (recipe1ContainsIngredientsThatArePastBestBeforeDates && recipe2ContainsIngredientsThatArePastBestBeforeDates) {
                return 0;
            }
            if (recipe1ContainsIngredientsThatArePastBestBeforeDates) {
                return 1;
            }
            return -1;
        }

        private boolean recipeContainsIngredientsThatArePastBestBeforeDates(Recipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredientsThatPastBestBeforeDates.contains(ingredient)) {
                    return true;
                }
            }
            return false;
        }
    }
}
