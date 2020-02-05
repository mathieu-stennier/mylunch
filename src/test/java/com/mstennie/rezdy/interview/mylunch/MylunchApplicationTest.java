package com.mstennie.rezdy.interview.mylunch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MylunchApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnStatusCode200WhenInvokingLunchEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lunch"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString(
                "{\"recipes\":[{\"title\":\"Ham and Cheese Toastie\",\"ingredients\":[\"Bread\",\"Ham\",\"Butter\",\"Cheese\"]},{\"title\":\"Hotdog\",\"ingredients\":[\"Sausage\",\"Ketchup\",\"Mustard\",\"Hotdog Bun\"]}]}")
                        )
                );
    }
}
