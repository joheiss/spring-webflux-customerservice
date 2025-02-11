package com.jovisco.tutorial.webflux.customerservice.customer;

public record CustomerResponseDto(
        Integer id,
        String name,
        Integer balance
) {
}
