package com.jovisco.tutorial.webflux.customerservice.customer;

import com.jovisco.tutorial.webflux.customerservice.exception.ApplicationExceptionsFactory;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CustomerRequestValidator {

    public static UnaryOperator<Mono<CustomerRequestDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptionsFactory.missingName())
                .filter(hasBalance())
                .switchIfEmpty(ApplicationExceptionsFactory.missingBalance());
    }

    private static Predicate<CustomerRequestDto> hasName() {
        return dto -> Objects.nonNull(dto.name());
    }

    private static Predicate<CustomerRequestDto> hasBalance() {
        return dto -> Objects.nonNull(dto.balance());
    }
}
