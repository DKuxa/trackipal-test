package org.kuxa.trackipaltest;

import org.junit.jupiter.api.Test;
import org.kuxa.trackipaltest.model.Order;
import org.kuxa.trackipaltest.repository.OrderRepository;
import org.kuxa.trackipaltest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
//@Profile("mock")
public class TrackipalTestApplicationTests {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(context, "The application context should load without errors.");
        assertNotNull(orderService, "The OrderService bean should be correctly wired and not null.");
//        assertNotNull(mockMvc, "MockMvc should not be null");
    }

    @Test
    public void whenListOrders_thenOrdersShouldBeReturned() throws Exception {
        // Arrange - create mock Orders
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

        given(orderService.listAllOrders()).willReturn(mockOrders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("orders", mockOrders))
                .andExpect(view().name("orders"));
    }
}
