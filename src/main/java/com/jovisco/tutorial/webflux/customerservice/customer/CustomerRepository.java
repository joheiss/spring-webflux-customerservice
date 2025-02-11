package com.jovisco.tutorial.webflux.customerservice.customer;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CustomerRepository extends R2dbcRepository<Customer, Integer> {
}
