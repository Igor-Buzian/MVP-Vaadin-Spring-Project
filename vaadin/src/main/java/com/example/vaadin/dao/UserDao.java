package com.example.vaadin.dao;

import com.example.vaadin.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getById(long id);
    void save(User user);
    void  update(User user);
    void delete(User user);
    List<User> getAll();
}

