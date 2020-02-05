package com.mstennie.rezdy.interview.mylunch.model.ingredient.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.Ingredient;
import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
import com.mstennie.rezdy.interview.mylunch.util.webclient.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;

/**
 * This implementation of IngredientRepository will get the ingredients instances
 * from an API call to an URL defined in our application properties.
 */

@Repository
public class APIIngredientRepository implements IngredientRepository {
    private WebClient ingredientsClient;

    public APIIngredientRepository(@Value("${api.ingredientrepository.url}") String url) {
        ingredientsClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public List<IngredientInstance> getAvailableIngredients() {
        IngredientJsonResponse[] ingredientsResponse = ingredientsClient.get()
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> JsonMapper.asArray(jsonNode.at("/ingredients"), IngredientJsonResponse[].class)).block();

        if (ingredientsResponse == null) {
            throw new RuntimeException("Unknown error while retrieving ingredients from the fridge.");
        }

        return fromJson(ingredientsResponse);
    }

    /**
     * We transform the json object retrieved with the actual service object.
     * We do this to not expose the specifics of the Json Object Mapper annotations to the world and have a clean immutable object
     * to return to the caller.
     */
    private List<IngredientInstance> fromJson(IngredientJsonResponse[] ingredientsJson) {
        List<IngredientInstance> ingredientInstances = new LinkedList<>();
        for (int i = 0; i < ingredientsJson.length; i++) {
            ingredientInstances.add(new IngredientInstance(new Ingredient(ingredientsJson[i].getTitle()), ingredientsJson[i].getBestBefore(), ingredientsJson[i].getUseBefore()));
        }
        return ingredientInstances;
    }
}
