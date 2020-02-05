package com.mstennie.rezdy.interview.mylunch.model.recipe.service;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import com.mstennie.rezdy.interview.mylunch.model.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceImplTest {

    @MockBean
    private RecipeRepository recipeRepository;

    private static Recipe recipe1 = new Recipe("recipe1", Ingredient.fromName("i1"), Ingredient.fromName("i2"), Ingredient.fromName("i3"));
    private static Recipe recipe2 = new Recipe("recipe2", Ingredient.fromName("i4"), Ingredient.fromName("i5"));
    private static Recipe recipe3 = new Recipe("recipe3", Ingredient.fromName("i1"), Ingredient.fromName("i2"));

    @Autowired
    RecipeService recipeService;

    @BeforeEach
    public void beforeAll() {
        List<Recipe> recipes = new LinkedList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
        Mockito.when(recipeRepository.getAllRecipes()).thenReturn(recipes);
    }

    @Test
    void getAllRecipes() {
        List<Recipe> recipes = new LinkedList<>();
        recipes.add(new Recipe("recipe1", Ingredient.fromName("i1"), Ingredient.fromName("i2"), Ingredient.fromName("i3")));
        recipes.add(new Recipe("recipe2", Ingredient.fromName("i4"), Ingredient.fromName("i5")));
        recipes.add(new Recipe("recipe3", Ingredient.fromName("i1"), Ingredient.fromName("i2")));
        assertEquals(recipes, recipeService.getAllRecipes(), "Recipe list does not contain the recipes from the repository.");
    }

    @Test
    void getMatchingRecipesForIngredients_All() {
        LocalDate now = LocalDate.now();

        // Past before Date, Prior to expiry date
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("i1"), now.minus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Fresh
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("i2"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Expired
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("i3"), now.minus(2, ChronoUnit.DAYS), now.minus(1, ChronoUnit.DAYS));

        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3};

        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(Arrays.asList(ingredientInstances), RecipeIngredientMatchingStrategy.ALL);

        assertEquals(2, matchingRecipesForIngredients.size());
        assertTrue(matchingRecipesForIngredients.contains(recipe1));
        assertTrue(matchingRecipesForIngredients.contains(recipe3));
    }

    @Test
    void getMatchingRecipesForIngredients_NotExpired() {
        LocalDate now = LocalDate.now();

        // Past before Date, Prior to expiry date
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("i1"), now.minus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Fresh
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("i2"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Expired
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("i3"), now.minus(2, ChronoUnit.DAYS), now.minus(1, ChronoUnit.DAYS));

        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3};

        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(Arrays.asList(ingredientInstances), RecipeIngredientMatchingStrategy.NOT_EXPIRED);

        assertEquals(1, matchingRecipesForIngredients.size());
        assertTrue(matchingRecipesForIngredients.contains(recipe3));
    }

    @Test
    void getMatchingRecipesForIngredients_Fresh() {
        LocalDate now = LocalDate.now();

        // Past before Date, Prior to expiry date
        // ------NONE
        // Fresh
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("i1"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("i2"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Expired
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("i3"), now.minus(2, ChronoUnit.DAYS), now.minus(1, ChronoUnit.DAYS));

        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3};

        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(Arrays.asList(ingredientInstances), RecipeIngredientMatchingStrategy.FRESH);

        assertEquals(1, matchingRecipesForIngredients.size());
        assertTrue(matchingRecipesForIngredients.contains(recipe3), "maching recipes should contain recipe3");
    }

    @Test
    void getMatchingRecipesForIngredients_Fresh2() {
        LocalDate now = LocalDate.now();

        // Past before Date, Prior to expiry date
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("i3"), now.minus(1, ChronoUnit.DAYS), now.plus(1, ChronoUnit.DAYS));
        // Fresh
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("i1"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("i2"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Expired
        // ------NONE

        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3};

        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(Arrays.asList(ingredientInstances), RecipeIngredientMatchingStrategy.FRESH);

        assertEquals(1, matchingRecipesForIngredients.size());
        assertTrue(matchingRecipesForIngredients.contains(recipe3), "matching recipes should contain recipe3");
    }

    @Test
    void getMatchingRecipesForIngredients_NotExpired_SortingFreshest() {
        LocalDate now = LocalDate.now();

        // Past before Date, Prior to expiry date
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("i3"), now.minus(1, ChronoUnit.DAYS), now.plus(1, ChronoUnit.DAYS));
        // Fresh
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("i1"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("i2"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        // Expired
        IngredientInstance i4 = new IngredientInstance(Ingredient.fromName("i4"), now.minus(2, ChronoUnit.DAYS), now.minus(1, ChronoUnit.DAYS));
        IngredientInstance i5 = new IngredientInstance(Ingredient.fromName("i5"), now.minus(2, ChronoUnit.DAYS), now.minus(1, ChronoUnit.DAYS));

        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3,i4,i5};

        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(Arrays.asList(ingredientInstances), RecipeIngredientMatchingStrategy.NOT_EXPIRED, RecipeIngredientSortingStrategy.FRESHEST);

        assertEquals(2, matchingRecipesForIngredients.size());
        assertTrue(matchingRecipesForIngredients.contains(recipe3), "matching recipes should contain recipe3");
        assertTrue(matchingRecipesForIngredients.contains(recipe1), "matching recipes should contain recipe1");
        assertFalse(matchingRecipesForIngredients.contains(recipe2), "matching recipes should not contain recipe2");
        assertEquals(recipe3, matchingRecipesForIngredients.get(0), "matching recipes should contain recipe3 as first in the list");
        assertEquals(recipe1, matchingRecipesForIngredients.get(1), "matching recipes should contain recipe1 as last in the list");
    }

    @Test
    void getMatchingRecipesForIngredients_NotExpired_SortingFreshest2() {
        LocalDate now = LocalDate.now();

        // Past before Date, Prior to expiry date
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("i3"), now.minus(1, ChronoUnit.DAYS), now.plus(1, ChronoUnit.DAYS));
        // Fresh
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("i1"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("i2"), now.plus(1, ChronoUnit.DAYS), now.plus(2, ChronoUnit.DAYS));
        IngredientInstance i4 = new IngredientInstance(Ingredient.fromName("i4"), now.plus(2, ChronoUnit.DAYS), now.plus(1, ChronoUnit.DAYS));
        IngredientInstance i5 = new IngredientInstance(Ingredient.fromName("i5"), now.plus(2, ChronoUnit.DAYS), now.plus(1, ChronoUnit.DAYS));


        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3,i4,i5};

        List<Recipe> matchingRecipesForIngredients = recipeService.getMatchingRecipesForIngredients(Arrays.asList(ingredientInstances), RecipeIngredientMatchingStrategy.NOT_EXPIRED, RecipeIngredientSortingStrategy.FRESHEST);

        assertEquals(3, matchingRecipesForIngredients.size());
        assertTrue(matchingRecipesForIngredients.contains(recipe3), "matching recipes should contain recipe3");
        assertTrue(matchingRecipesForIngredients.contains(recipe1), "matching recipes should contain recipe1");
        assertTrue(matchingRecipesForIngredients.contains(recipe2), "matching recipes should contain recipe2");
        assertEquals(recipe1, matchingRecipesForIngredients.get(2), "matching recipes should contain recipe1 as last in the list");
    }
}
