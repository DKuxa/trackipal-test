package org.kuxa.trackipaltest.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.kuxa.trackipaltest.model.Order;

import java.util.List;

@Setter
@Getter
public class OrderResponseWrapper {
    private List<Order> orders;
}
