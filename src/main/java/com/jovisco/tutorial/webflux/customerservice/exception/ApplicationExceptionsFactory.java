package com.jovisco.tutorial.webflux.customerservice.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptionsFactory {

    public static <T> Mono<T> customerNotFound(Integer id) {
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> missingName() {
        return Mono.error(new InvalidInputException("Name is required"));
    }

    public static <T> Mono<T> missingBalance() {
        return Mono.error(new InvalidInputException("Balance is required"));
    }

    public static <T> Mono<T> missingCustomerId() {
        return Mono.error(new InvalidInputException("Customer id is required"));
    }

    public static <T> Mono<T> missingTicker() {
        return Mono.error(new InvalidInputException("Ticker is required"));
    }

    public static <T> Mono<T> missingQuantity() {
        return Mono.error(new InvalidInputException("Quantity is required"));
    }

    public static <T> Mono<T> insufficientBalance(Integer customerId){
        return Mono.error(new InsufficientBalanceException(customerId));
    }

    public static <T> Mono<T> insufficientShares(Integer customerId){
        return Mono.error(new InsufficientSharesException(customerId));
    }

}
