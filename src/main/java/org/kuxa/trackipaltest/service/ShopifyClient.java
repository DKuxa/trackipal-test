package org.kuxa.trackipaltest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ShopifyClient {
    private static final Logger log = LoggerFactory.getLogger(ShopifyClient.class);
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String storeUrl;

    public ShopifyClient(@Value("${shopify.api-key}") String apiKey,
                         @Value("${shopify.store-url}") String storeUrl,
                         RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.storeUrl = storeUrl;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getAllOrders() {
        String url = storeUrl + "/admin/api/2024-04/orders.json?status=any";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("API Error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e; // Rethrow the exception to be handled by the service layer
        }
    }
}