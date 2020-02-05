package com.mstennie.rezdy.interview.mylunch.model.recipe.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;
import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import com.mstennie.rezdy.interview.mylunch.util.webclient.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class APIRecipeRepository implements RecipeRepository{

    private WebClient recipesClient;

    public APIRecipeRepository(@Value("${api.reciperepository.url}") String url) {
        recipesClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        RecipeJsonResponse[] recipeJsonResponse = recipesClient.get()
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> JsonMapper.asArray(jsonNode.at("/recipes"), RecipeJsonResponse[].class)).block();

        if (recipeJsonResponse == null) {
            throw new RuntimeException("Unknown error while retrieving recipes from server.");
        }

        return Collections.unmodifiableList(fromJson(recipeJsonResponse));
    }

    /**
     * We transform the json object retrieved into the actual service object.
     * We do this to not expose the specifics of the Json Object Mapper annotations to the world and have a clean immutable object
     * to return to the caller.
     */
    private List<Recipe> fromJson(RecipeJsonResponse[] recipesJson) {
        List<Recipe> recipes = new LinkedList<>();
        for (int i = 0; i < recipesJson.length; i++) {
            Set<Ingredient> ingredients = Arrays.stream(recipesJson[i].getIngredients()).map(Ingredient::new).collect(Collectors.toSet());
            recipes.add(new Recipe(recipesJson[i].getTitle(), ingredients));
        }
        return recipes;
    }
}
