package org.kuxa.trackipaltest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kuxa.trackipaltest.model.Order;
import org.kuxa.trackipaltest.repository.OrderRepository;
import org.kuxa.trackipaltest.wrapper.OrderResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final ShopifyClient shopifyClient;
    @Autowired
    public OrderService(ShopifyClient shopifyClient) {
        this.shopifyClient = shopifyClient;
    }

    public List<Order> listAllOrders() throws JsonProcessingException {
        ResponseEntity<String> response = shopifyClient.getAllOrders();
        ObjectMapper objectMapper = new ObjectMapper();
        OrderResponseWrapper wrapper = objectMapper.readValue(response.getBody(), OrderResponseWrapper.class);
        return wrapper.getOrders();
    }


    public void markOrderAsFulfilled(Long orderId, String trackingCode) throws Exception {
        if (!shopifyClient.orderExists(orderId)) {
            throw new NoSuchElementException("Order not found");
        }
        shopifyClient.fulfillOrderInShopify(orderId, trackingCode);
    }

}
