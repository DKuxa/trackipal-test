package org.kuxa.trackipaltest.repository;

import org.kuxa.trackipaltest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
