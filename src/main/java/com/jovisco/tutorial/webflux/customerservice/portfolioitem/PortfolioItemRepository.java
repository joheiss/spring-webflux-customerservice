package com.jovisco.tutorial.webflux.customerservice.portfolioitem;

import com.jovisco.tutorial.webflux.customerservice.trade.Ticker;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PortfolioItemRepository extends R2dbcRepository<PortfolioItem, Integer> {

    Flux<PortfolioItem> findAllByCustomerId(Integer customerId);
    Mono<PortfolioItem> findByCustomerIdAndTicker(Integer customerId, Ticker ticker);
}
