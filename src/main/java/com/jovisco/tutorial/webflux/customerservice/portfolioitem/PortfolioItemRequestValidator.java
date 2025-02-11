package com.jovisco.tutorial.webflux.customerservice.portfolioitem;

import com.jovisco.tutorial.webflux.customerservice.exception.ApplicationExceptionsFactory;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class PortfolioItemRequestValidator {

    public static UnaryOperator<Mono<PortfolioItemRequestDto>> validate() {
        return mono -> mono.filter(hasCustomerId())
                .switchIfEmpty(ApplicationExceptionsFactory.missingCustomerId())
                .filter(hasTicker())
                .switchIfEmpty(ApplicationExceptionsFactory.missingTicker())
                .filter(hasQuantity())
                .switchIfEmpty(ApplicationExceptionsFactory.missingQuantity());
    }

    private static Predicate<PortfolioItemRequestDto> hasCustomerId() {
        return dto -> Objects.nonNull(dto.customerId());
    }

    private static Predicate<PortfolioItemRequestDto> hasTicker() {
        return dto -> Objects.nonNull(dto.ticker());
    }

    private static Predicate<PortfolioItemRequestDto> hasQuantity() {
        return dto -> Objects.nonNull(dto.quantity());
    }
}
