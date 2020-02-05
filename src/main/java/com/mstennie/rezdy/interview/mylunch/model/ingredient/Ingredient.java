package com.mstennie.rezdy.interview.mylunch.model.ingredient;

import java.util.Objects;

/**
 * Represent one Ingredient in its general form. an "orange", "salt",...
 */
public class Ingredient {
    private String name;

    public static Ingredient fromName(String name) {
        return new Ingredient(name);
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
