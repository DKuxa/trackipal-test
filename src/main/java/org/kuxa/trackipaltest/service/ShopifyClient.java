package org.kuxa.trackipaltest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class ShopifyClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopifyClient.class);
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
        String url = storeUrl + "/admin/api/2023-04/orders.json?status=any";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            LOGGER.error("API Error: {}", e.getMessage());
            throw e; // Rethrow the exception to be handled by the service layer
        }
    }

    public boolean orderExists(Long orderId) {
        String url = storeUrl + "/admin/api/2023-04/orders/" + orderId + ".json";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        LOGGER.debug("entity: {}", entity);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            LOGGER.debug("response: {}", response);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RestClientException e) {
            LOGGER.error("Failed to check if order exists: {}", e.getMessage());
            return false;
        }
    }

    public String createFulfillmentJson(Long orderId, String trackingNumber) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> trackingInfo = new HashMap<>();
            trackingInfo.put("number", trackingNumber);
            // TODO: Update this URL to the actual tracking URL provided by your shipping company
            trackingInfo.put("url", "https://www.your-shipping-company.com?tracking_number=" + trackingNumber);

            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("fulfillment_order_id", getFulfillmentOrderId(orderId));
            lineItem.put("tracking_info", trackingInfo);

            List<Map<String, Object>> lineItemsByFulfillmentOrder = new ArrayList<>();
            lineItemsByFulfillmentOrder.add(lineItem);

            Map<String, Object> fulfillment = new HashMap<>();
            // TODO: Replace with actual location ID
            fulfillment.put("location_id", 73664004227L);
            fulfillment.put("line_items_by_fulfillment_order", lineItemsByFulfillmentOrder);
            fulfillment.put("notify_customer", true);

            Map<String, Object> rootMap = new HashMap<>();
            rootMap.put("fulfillment", fulfillment);

            return mapper.writeValueAsString(rootMap);
        } catch (Exception e) {
            LOGGER.error("Error while creating Fulfillment Json: {}", e.getMessage());
            return "{}";
        }
    }

    public Long getFulfillmentOrderId(Long orderId) throws Exception {
        String url = storeUrl + "/admin/api/2024-04/orders/" + orderId + "/fulfillment_orders.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Shopify-Access-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        JsonNode fulfillmentOrderNode = getJsonNode(orderId, response);
        return fulfillmentOrderNode.get("id").asLong();
    }

    private static JsonNode getJsonNode(Long orderId, ResponseEntity<String> response) throws JsonProcessingException {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new HttpClientErrorException(response.getStatusCode(),
                    "Error response while fetching fulfillment orders: " +
                            response.getBody());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode fulfillmentOrdersNode = root.path("fulfillment_orders");

        if (!fulfillmentOrdersNode.isArray() || fulfillmentOrdersNode.isEmpty()) {
            throw new NoSuchElementException("No fulfillment orders found for order id: " + orderId);
        }

        // Assuming you want the ID of the first fulfillment order
        return fulfillmentOrdersNode.get(0);
    }


    public void fulfillOrderInShopify(Long orderId, String trackingNumber) {
        String url = storeUrl + "/admin/api/2024-04/fulfillments.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Shopify-Access-Token", apiKey);

        String jsonBody = createFulfillmentJson(orderId, trackingNumber);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOGGER.error("Shopify API returned unsuccessful response: {}", response.getBody());
            }
        } catch (RestClientException e) {
            LOGGER.error("Error while fulfilling order in Shopify: {}", e.getMessage());
            throw e;
        }
    }
}
