package com.jovisco.tutorial.webflux.customerservice.customer;

import com.jovisco.tutorial.webflux.customerservice.portfolioitem.PortfolioItemRepository;
import com.jovisco.tutorial.webflux.customerservice.exception.ApplicationExceptionsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            PortfolioItemRepository portfolioItemRepository
    ) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Override
    public Mono<CustomerResponseDto> getCustomer(Integer id) {
        return customerRepository.findById(id)
                .switchIfEmpty(ApplicationExceptionsFactory.customerNotFound(id))
                .map(CustomerMapper::toResponseDto);
    }

    @Override
    public Mono<CustomerInformationResponseDto> getCustomerInformation(Integer id) {
       return customerRepository.findById(id)
                .switchIfEmpty(ApplicationExceptionsFactory.customerNotFound(id))
                .flatMap(this::buildCustomerInformation);
    }

    private Mono<CustomerInformationResponseDto> buildCustomerInformation(Customer customer) {
        return portfolioItemRepository.findAllByCustomerId(customer.getId())
                .collectList()
                .map(list -> CustomerMapper.toCustomerInformationResponseDto(
                        customer, list));
    }
}
