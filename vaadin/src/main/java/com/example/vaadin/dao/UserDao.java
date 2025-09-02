package com.example.vaadin.dao;

import com.example.vaadin.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao {
    Optional<User> getById(Long id);
    void save(User user);
    void  update(User user);
    void delete(User user);
    List<User> getAll();
}

