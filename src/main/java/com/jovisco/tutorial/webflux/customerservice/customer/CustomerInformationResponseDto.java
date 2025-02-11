package com.jovisco.tutorial.webflux.customerservice.customer;

import java.util.List;

public record CustomerInformationResponseDto(
        Integer id,
        String name,
        Integer balance,
        List<CustomerHoldingResponseDto> holdings
) {
}
