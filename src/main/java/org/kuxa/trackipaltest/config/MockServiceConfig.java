package org.kuxa.trackipaltest.config;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.kuxa.trackipaltest.service.OrderService;
import org.kuxa.trackipaltest.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@Profile("mock")
public class MockServiceConfig {

    @Bean
    public OrderService orderService() throws JsonProcessingException {
        OrderService mockService = mock(OrderService.class);

        // Create mock data that corresponds to the example Shopify response
        Order mockOrder1 = new Order();
        mockOrder1.setShopifyOrderId(450789469L);
        mockOrder1.setOrderNumber("#1001");
        mockOrder1.setOrderDate(new Date());
        mockOrder1.setTotal(199.65);
        mockOrder1.setFinancialStatus("partially_refunded");
        mockOrder1.setFulfillmentStatus(null);
        mockOrder1.setFulfilled(false);
        mockOrder1.setTrackingCode(null);

        Order mockOrder2 = new Order();
        mockOrder2.setShopifyOrderId(450789470L);
        mockOrder2.setOrderNumber("#1002");
        mockOrder2.setOrderDate(new Date());
        mockOrder2.setTotal(299.99);
        mockOrder2.setFinancialStatus("paid");
        mockOrder2.setFulfillmentStatus("fulfilled");
        mockOrder2.setFulfilled(true);
        mockOrder2.setTrackingCode("1Z2345");

        List<Order> mockOrders = Arrays.asList(mockOrder1, mockOrder2);

        when(mockService.listAllOrders()).thenReturn(mockOrders);

        return mockService;
    }
}
