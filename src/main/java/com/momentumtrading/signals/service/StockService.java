package com.momentumtrading.signals.service;

import com.momentumtrading.signals.model.Datum;
import com.momentumtrading.signals.model.Listing;
import com.momentumtrading.signals.utils.HttpsSteps;
import com.momentumtrading.signals.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class StockService {


    public static void main(String[] args) {
        HttpsSteps steps  = new HttpsSteps();
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

        Response response = steps
                .withRestEndpoint(uri, "GET")
                .withParams("start","1")
                .withParams("limit","5")
                .withParams("convert","USD")
                .withHeader("X-CMC_PRO_API_KEY","b9a95346-ceca-4759-ae11-82a7de9e45c5")
                .expectStatus(200)
                .execute();

    }
}
