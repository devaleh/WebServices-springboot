package com.devaleh.webservices.repositories;

import com.devaleh.webservices.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
