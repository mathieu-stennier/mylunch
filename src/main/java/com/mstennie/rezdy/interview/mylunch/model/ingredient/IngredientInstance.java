package com.mstennie.rezdy.interview.mylunch.model.ingredient;

import java.time.LocalDate;

public class IngredientInstance {
    private Ingredient ingredient;
    private LocalDate bestBefore;
    private LocalDate useBefore;

    public IngredientInstance(Ingredient ingredient, LocalDate bestBefore, LocalDate useBefore) {
        this.ingredient = ingredient;
        this.bestBefore = bestBefore;
        this.useBefore = useBefore;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public LocalDate getUseBefore() {
        return useBefore;
    }
}
