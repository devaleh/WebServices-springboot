package com.devaleh.webservices.repositories;

import com.devaleh.webservices.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
