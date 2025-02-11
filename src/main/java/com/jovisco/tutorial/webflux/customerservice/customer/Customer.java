package com.jovisco.tutorial.webflux.customerservice.customer;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "trading_customers")
public class Customer {

    @Id
    private Integer id;
    private String name;
    private Integer balance;

}
