package com.devaleh.webservices.controllers;

import com.devaleh.webservices.entities.Product;
import com.devaleh.webservices.entities.User;
import com.devaleh.webservices.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productsList = service.findAll();
        for (Product product : productsList) {
            Long id = product.getId();
            product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
        }
        return ResponseEntity.ok().body(productsList);
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable Long id) {
        Product product = service.findById(id);
        product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        product = service.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product = service.update(id, product);
        return ResponseEntity.ok().body(product);
    }
}
