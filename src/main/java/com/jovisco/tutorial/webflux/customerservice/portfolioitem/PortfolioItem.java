package com.jovisco.tutorial.webflux.customerservice.portfolioitem;

import com.jovisco.tutorial.webflux.customerservice.trade.Ticker;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "portfolio_items")
public class PortfolioItem {

    @Id
    private Integer id;
    private Integer customerId;
    private Ticker ticker;
    private Integer quantity;
}
