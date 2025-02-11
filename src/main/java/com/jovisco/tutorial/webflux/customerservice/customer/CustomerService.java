package com.jovisco.tutorial.webflux.customerservice.customer;

import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerResponseDto> getCustomer(Integer id);
    Mono<CustomerInformationResponseDto> getCustomerInformation(Integer id);
}
