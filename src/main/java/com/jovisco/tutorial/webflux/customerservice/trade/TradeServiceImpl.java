package com.jovisco.tutorial.webflux.customerservice.trade;

import com.jovisco.tutorial.webflux.customerservice.customer.Customer;
import com.jovisco.tutorial.webflux.customerservice.customer.CustomerRepository;
import com.jovisco.tutorial.webflux.customerservice.exception.ApplicationExceptionsFactory;
import com.jovisco.tutorial.webflux.customerservice.portfolioitem.PortfolioItem;
import com.jovisco.tutorial.webflux.customerservice.portfolioitem.PortfolioItemMapper;
import com.jovisco.tutorial.webflux.customerservice.portfolioitem.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class TradeServiceImpl implements TradeService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public TradeServiceImpl(
            CustomerRepository customerRepository,
            PortfolioItemRepository portfolioItemRepository
    ) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Transactional
    @Override
    public Mono<TradeResponseDto> createTrade(Integer customerId, TradeRequestDto request) {
        return switch (request.action()) {
            case BUY -> this.buyStock(customerId, request);
            case SELL -> this.sellStock(customerId, request);
        };
    }

    private Mono<TradeResponseDto> buyStock(Integer customerId, TradeRequestDto request) {
        var customerMono = this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptionsFactory.customerNotFound(customerId))
                .filter(c -> c.getBalance() >= request.totalPrice())
                .switchIfEmpty(ApplicationExceptionsFactory.insufficientBalance(customerId));

        var portfolioItemMono = this.portfolioItemRepository.findByCustomerIdAndTicker(customerId, request.ticker())
                .defaultIfEmpty(PortfolioItemMapper.toEntity(customerId, request.ticker()));

        return customerMono.zipWhen(customer -> portfolioItemMono)
                .flatMap(t -> this.executeBuy(t.getT1(), t.getT2(), request));

    }

    private Mono<TradeResponseDto> executeBuy(Customer customer, PortfolioItem portfolioItem, TradeRequestDto request) {
        customer.setBalance(customer.getBalance() - request.totalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity() + request.quantity());
        return this.saveAndBuildResponse(customer, portfolioItem, request);
    }

    private Mono<TradeResponseDto> sellStock(Integer customerId, TradeRequestDto request) {
        var customerMono = this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptionsFactory.customerNotFound(customerId));

        var portfolioItemMono = this.portfolioItemRepository.findByCustomerIdAndTicker(customerId, request.ticker())
                .filter(pi -> pi.getQuantity() >= request.quantity())
                .switchIfEmpty(ApplicationExceptionsFactory.insufficientShares(customerId));

        return customerMono.zipWhen(customer -> portfolioItemMono)
                .flatMap(t -> this.executeSell(t.getT1(), t.getT2(), request));
    }

    private Mono<TradeResponseDto> executeSell(Customer customer, PortfolioItem portfolioItem, TradeRequestDto request) {
        customer.setBalance(customer.getBalance() + request.totalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity() - request.quantity());
        return this.saveAndBuildResponse(customer, portfolioItem, request);
    }

    private Mono<TradeResponseDto> saveAndBuildResponse(Customer customer, PortfolioItem portfolioItem, TradeRequestDto request) {
        var response = TradeMapper.toResponseDto(request, customer);
        return Mono.zip(
                this.customerRepository.save(customer),
                this.portfolioItemRepository.save(portfolioItem)
                )
                .thenReturn(response);
    }
}
