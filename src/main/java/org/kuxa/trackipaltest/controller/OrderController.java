package org.kuxa.trackipaltest.controller;

import org.kuxa.trackipaltest.model.Order;
import org.kuxa.trackipaltest.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        LOGGER.info("Handling /orders request.");
        List<Order> orders = orderService.listAllOrders();
        orders.forEach(order -> LOGGER.debug("Order: {}", order));
        model.addAttribute("orders", orders);
        return "orders";
    }
}