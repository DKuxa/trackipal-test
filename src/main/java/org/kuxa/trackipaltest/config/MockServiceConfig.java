package org.kuxa.trackipaltest.config;
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
    public OrderService orderService() {
        OrderService mockService = mock(OrderService.class);

        Order mockOrder1 = new Order();
        mockOrder1.setId(1L);
        mockOrder1.setOrderNumber(123L);
        mockOrder1.setOrderDate(new Date());
        mockOrder1.setTotal(100.00);
        mockOrder1.setFinancialStatus("Paid");
        mockOrder1.setFulfillmentStatus("Fulfilled");
        mockOrder1.setFulfilled(true);
        mockOrder1.setTrackingCode("12345");

        Order mockOrder2 = new Order();
        mockOrder2.setId(2L);
        mockOrder2.setOrderNumber(456L);
        mockOrder2.setOrderDate(new Date());
        mockOrder2.setTotal(200.00);
        mockOrder2.setFinancialStatus("Pending");
        mockOrder2.setFulfillmentStatus("Unfulfilled");
        mockOrder2.setFulfilled(false);
        mockOrder2.setTrackingCode(null);

        List<Order> mockOrders = Arrays.asList(mockOrder1, mockOrder2);

        when(mockService.listAllOrders()).thenReturn(mockOrders);

        return mockService;
    }
}