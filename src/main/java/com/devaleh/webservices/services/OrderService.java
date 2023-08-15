package com.devaleh.webservices.services;

import com.devaleh.webservices.entities.Order;
import com.devaleh.webservices.repositories.OrderRepository;
import com.devaleh.webservices.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
       Optional<Order> order = repository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
