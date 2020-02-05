package com.mstennie.rezdy.interview.mylunch.model.recipe.repository;

import com.mstennie.rezdy.interview.mylunch.model.recipe.Recipe;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"api.reciperepository.url=http://localhost:8081"})
@ContextConfiguration(classes = APIRecipeRepository.class, loader = AnnotationConfigContextLoader.class)
class APIRecipeRepositoryTest {

    public static MockWebServer mockBackEnd;

    @Autowired
    public RecipeRepository recipeRepository;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8081);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getAllRecipes() {
        mockBackEnd.enqueue(new MockResponse().setBody("{\n" +
                "    \"recipes\": [{\n" +
                "\t\t\"title\": \"Ham and Cheese Toastie\",\n" +
                "\t\t\"ingredients\": [\"Ham\", \"Cheese\", \"Bread\", \"Butter\"]\n" +
                "\t}, {\n" +
                "\t\t\"title\": \"Fry-up\",\n" +
                "\t\t\"ingredients\": [\"Bacon\", \"Eggs\", \"Baked Beans\", \"Mushrooms\", \"Sausage\", \"Bread\"]\n" +
                "\t}, {\n" +
                "\t\t\"title\": \"Salad\",\n" +
                "\t\t\"ingredients\": [\"Lettuce\", \"Tomato\", \"Cucumber\", \"Beetroot\", \"Salad Dressing\"]\n" +
                "\t}, {\n" +
                "\t\t\"title\": \"Hotdog\",\n" +
                "\t\t\"ingredients\": [\"Hotdog Bun\", \"Sausage\", \"Ketchup\", \"Mustard\"]\n" +
                "\t}, {\n" +
                "\t\t\"title\": \"Omelette\",\n" +
                "\t\t\"ingredients\": [\"Eggs\", \"Mushrooms\", \"Milk\", \"Salt\", \"Pepper\", \"Spinach\"]\n" +
                "\t}]\n" +
                "}").setResponseCode(200).setHeader("Content-Type", "application/json"));

        List<Recipe> allRecipes = recipeRepository.getAllRecipes();

        assertEquals(5, allRecipes.size());
    }
}
