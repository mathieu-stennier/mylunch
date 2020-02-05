package com.mstennie.rezdy.interview.mylunch.model.recipe.repository;

public class RepositoryNotAvailableException extends RuntimeException{

    public RepositoryNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
