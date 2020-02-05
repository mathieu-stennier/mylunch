package com.mstennie.rezdy.interview.mylunch.controller.lunch;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.repository.IngredientRepository;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import com.mstennie.rezdy.interview.mylunch.model.recipe.repository.RecipeJsonResponse;
import com.mstennie.rezdy.interview.mylunch.model.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LunchControllerTest {

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Autowired
    private LunchController lunchController;

    @Test
    void lunch() {
        IngredientInstance i1 = new IngredientInstance(Ingredient.fromName("ingredient1"), LocalDate.now().plus(1, ChronoUnit.DAYS), LocalDate.now().plus(2, ChronoUnit.DAYS));
        IngredientInstance i2 = new IngredientInstance(Ingredient.fromName("ingredient2"), LocalDate.now().plus(1, ChronoUnit.DAYS), LocalDate.now().plus(2, ChronoUnit.DAYS));
        IngredientInstance i3 = new IngredientInstance(Ingredient.fromName("ingredient3"), LocalDate.now().plus(1, ChronoUnit.DAYS), LocalDate.now().plus(2, ChronoUnit.DAYS));
        IngredientInstance[] ingredientInstances = new IngredientInstance[]{i1,i2,i3};
        Mockito.when(ingredientRepository.getAvailableIngredients()).thenReturn(Arrays.asList(ingredientInstances));

        Recipe recipe = new Recipe("recipe1", Ingredient.fromName("ingredient1"));
        Mockito.when(recipeService.getMatchingRecipesForIngredients(any(),any(),any())).thenReturn(Collections.singletonList(recipe));

        RecipeJsonResponse[] recipesJson = lunchController.lunch().getRecipesJson();

        assertEquals(1, recipesJson.length);
        assertEquals("recipe1", recipesJson[0].getTitle());
        assertEquals(1, recipesJson[0].getIngredients().length);
        assertEquals("ingredient1", recipesJson[0].getIngredients()[0]);
    }
}
