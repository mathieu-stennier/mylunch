package com.mstennie.rezdy.interview.mylunch.model.ingredient.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;

public class IngredientJsonResponse {
    @JsonProperty("title")
    private String title;

    @JsonProperty("best-before")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate bestBefore;

    @JsonProperty("use-by")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate useBefore;

    public IngredientJsonResponse(String title, LocalDate bestBefore, LocalDate useBefore) {
        this.title = title;
        this.bestBefore = bestBefore;
        this.useBefore = useBefore;
    }

    public IngredientJsonResponse() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
    }

    public LocalDate getUseBefore() {
        return useBefore;
    }

    public void setUseBefore(LocalDate useBefore) {
        this.useBefore = useBefore;
    }
}
