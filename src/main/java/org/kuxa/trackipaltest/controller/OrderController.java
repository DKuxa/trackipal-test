package org.kuxa.trackipaltest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kuxa.trackipaltest.model.Order;
import org.kuxa.trackipaltest.service.OrderService;
import org.kuxa.trackipaltest.service.ShopifyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final ShopifyClient shopifyService;


    public OrderController(OrderService orderService, ShopifyClient shopifyService) {
        this.orderService = orderService;
        this.shopifyService = shopifyService;
    }

    @GetMapping("/orders")
    public String listOrders(Model model) throws JsonProcessingException {
        List<Order> orders = orderService.listAllOrders();
        orders.forEach(order -> LOGGER.debug("Order: {}", order));
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders/{orderId}/fulfill")
    public ResponseEntity<?> fulfillOrder(@PathVariable Long orderId, @RequestParam String trackingCode) {
        try {
            orderService.markOrderAsFulfilled(orderId, trackingCode);
            return ResponseEntity.ok("Order fulfilled successfully");
        } catch (NoSuchElementException e) {
            LOGGER.error("Failed to fulfill order - not found: {}", orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to fulfill order - already fulfilled: {}", orderId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error fulfilling order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fulfilling order: " + e.getMessage());
        }
    }
}
