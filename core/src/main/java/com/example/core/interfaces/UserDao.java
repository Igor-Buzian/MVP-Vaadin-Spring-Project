package com.example.core.interfaces;


import com.example.core.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface UserDao {
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    void save(User user);
    void  update(User user);
    void delete(User user);
    List<User> getAll();
    boolean existsByEmail(String email);
}

