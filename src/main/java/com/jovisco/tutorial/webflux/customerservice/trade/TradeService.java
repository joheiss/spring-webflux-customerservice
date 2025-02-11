package com.jovisco.tutorial.webflux.customerservice.trade;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface TradeService {
    @Transactional
    Mono<TradeResponseDto> createTrade(Integer customerId, TradeRequestDto request);
}
