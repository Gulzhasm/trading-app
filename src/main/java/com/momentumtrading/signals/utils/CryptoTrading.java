package com.momentumtrading.signals.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CryptoTrading {
    private static final String BINANCE_API_URL = "https://api.binance.com/api/v3";

    public static void main(String[] args) {
        try {
            // Fetch price data
            String pair = "BTCUSDT"; // Example pair
            double price = fetchPrice(pair);
            System.out.println("Latest price of " + pair + ": $" + price);
        } catch (IOException e) {
            System.err.println("Error fetching price data: " + e.getMessage());
        }
    }

    private static double fetchPrice(String pair) throws IOException {
        URL url = new URL(BINANCE_API_URL + "/ticker/price?symbol=" + pair);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response and extract price
            String jsonResponse = response.toString();
            return parsePrice(jsonResponse);
        } else {
            throw new IOException("HTTP error code: " + responseCode);
        }
    }

    private static double parsePrice(String jsonResponse) {
        // Parse JSON response and extract price
        // Sample response: {"symbol":"BTCUSDT","price":"46284.64000000"}
        String priceString = jsonResponse.split("\"price\":\"")[1].split("\"")[0];
        return Double.parseDouble(priceString);
    }
}
