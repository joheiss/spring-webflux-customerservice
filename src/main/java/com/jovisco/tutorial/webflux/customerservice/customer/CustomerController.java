package com.jovisco.tutorial.webflux.customerservice.customer;

import com.jovisco.tutorial.webflux.customerservice.trade.TradeRequestDto;
import com.jovisco.tutorial.webflux.customerservice.trade.TradeResponseDto;
import com.jovisco.tutorial.webflux.customerservice.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;
    private final TradeService tradeService;

    @Autowired
    public CustomerController(CustomerService customerService, TradeService tradeService) {
        this.customerService = customerService;
        this.tradeService = tradeService;
    }

    @GetMapping("{id}")
    public Mono<CustomerInformationResponseDto> getCustomerInformation(@PathVariable Integer id) {
        return customerService.getCustomerInformation(id);
    }

    @PostMapping("{id}/trade")
    public Mono<TradeResponseDto> createTrade(
            @PathVariable Integer id,
            @RequestBody Mono<TradeRequestDto> trade
    ){
        return trade.flatMap(req -> this.tradeService.createTrade(id, req));
    }
}
