package org.Kuxa.trackipal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ShopifyClient {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String storeUrl;

    public ShopifyClient(RestTemplate restTemplate,
                         @Value("${shopify.api-key}") String apiKey,
                         @Value("${shopify.store-url}") String storeUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.storeUrl = storeUrl;
    }

    public String getAllOrders() {
        String url = storeUrl + "orders.json?status=any";
        return performApiCall(url);
    }

    private String performApiCall(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return "API Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }
}
