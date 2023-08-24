package com.devaleh.webservices.controllers;

import com.devaleh.webservices.entities.User;
import com.devaleh.webservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> usersList = service.findAll();
        for (User user : usersList) {
            Long id = user.getId();
            user.add(linkTo(methodOn(UserController.class).getOneUser(id)).withSelfRel());
        }
        return ResponseEntity.ok().body(usersList);
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable Long id) {
        User user = service.findById(id);
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("Users List"));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        user = service.save(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user = service.update(id, user);
        return ResponseEntity.ok().body(user);
    }
}
