package com.jovisco.tutorial.webflux.customerservice.customer;

public record CustomerRequestDto(
        String name,
        Integer balance
) {
}
