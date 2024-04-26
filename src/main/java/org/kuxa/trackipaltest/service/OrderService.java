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

import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ShopifyClient shopifyClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, ShopifyClient shopifyClient) {
        this.orderRepository = orderRepository;
        this.shopifyClient = shopifyClient;
    }

    public List<Order> listAllOrders() {
        ResponseEntity<String> response = shopifyClient.getAllOrders();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderResponseWrapper wrapper = objectMapper.readValue(response.getBody(), OrderResponseWrapper.class);
            return wrapper.getOrders();
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON", e);
            return Collections.emptyList();
        }
    }

}