package com.jovisco.tutorial.webflux.customerservice.customer;

import com.jovisco.tutorial.webflux.customerservice.portfolioitem.PortfolioItem;

import java.util.List;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequestDto customerRequestDto) {
        return Customer.builder()
                .name(customerRequestDto.name())
                .balance(customerRequestDto.balance())
                .build();
    }

    public static CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getBalance()
        );
    }

    public static CustomerInformationResponseDto toCustomerInformationResponseDto(
            Customer customer,
            List<PortfolioItem> items
    ) {
        var holdings = items.stream()
                .map(i -> new CustomerHoldingResponseDto(
                        i.getTicker(),
                        i.getQuantity()
                ))
                .toList();
        return new CustomerInformationResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getBalance(),
                holdings
        );
    }
}
