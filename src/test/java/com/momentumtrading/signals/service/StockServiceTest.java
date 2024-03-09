package com.momentumtrading.signals.service;

import com.momentumtrading.signals.model.StockWrapper;
import com.momentumtrading.signals.utils.HttpsSteps;
import com.momentumtrading.signals.utils.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;


@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Test
    void invoke() throws IOException {
//        final StockWrapper stock  = stockService.findStock("INTC");
//        System.out.println(stock.getStock());
        Stock stock = YahooFinance.get("INTC");
        BigDecimal price = stock.getQuote(true).getPrice();
        System.out.println(price);
    }

}