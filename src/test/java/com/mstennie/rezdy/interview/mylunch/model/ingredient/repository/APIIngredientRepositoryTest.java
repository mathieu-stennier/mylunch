package com.mstennie.rezdy.interview.mylunch.model.ingredient.repository;

import com.mstennie.rezdy.interview.mylunch.model.ingredient.IngredientInstance;
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
@TestPropertySource(properties = {"api.ingredientrepository.url=http://localhost:8082"})
@ContextConfiguration(classes = APIIngredientRepository.class, loader = AnnotationConfigContextLoader.class)
class APIIngredientRepositoryTest {

    public static MockWebServer mockBackEnd;

    @Autowired
    public IngredientRepository ingredientRepository;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8082);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getAllRecipes() {
        mockBackEnd.enqueue(new MockResponse().setBody("{\"ingredients\":[{\"title\":\"Ham\",\"best-before\":\"2020-02-13\",\"use-by\":\"2020-02-18\"},{\"title\":\"Cheese\",\"best-before\":\"2020-02-13\",\"use-by\":\"2020-02-18\"},{\"title\":\"Bread\",\"best-before\":\"2020-02-13\",\"use-by\":\"2020-02-18\"},{\"title\":\"Butter\",\"best-before\":\"2020-02-13\",\"use-by\":\"2020-02-18\"},{\"title\":\"Bacon\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Eggs\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Mushrooms\",\"best-before\":\"2020-01-29\",\"use-by\":\"2020-02-01\"},{\"title\":\"Sausage\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Hotdog Bun\",\"best-before\":\"2020-01-29\",\"use-by\":\"2020-02-13\"},{\"title\":\"Ketchup\",\"best-before\":\"2020-02-13\",\"use-by\":\"2020-02-18\"},{\"title\":\"Mustard\",\"best-before\":\"2020-02-13\",\"use-by\":\"2020-02-18\"},{\"title\":\"Lettuce\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Tomato\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Cucumber\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Beetroot\",\"best-before\":\"2020-02-08\",\"use-by\":\"2020-02-13\"},{\"title\":\"Salad Dressing\",\"best-before\":\"2020-01-29\",\"use-by\":\"2020-02-01\"}]}")
                .setResponseCode(200).setHeader("Content-Type", "application/json"));

        List<IngredientInstance> availableIngredients = ingredientRepository.getAvailableIngredients();

        assertEquals(16, availableIngredients.size());
    }
}
