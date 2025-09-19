package com.example.share.interfaces.interfaces;


import com.example.core.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserDao {
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    void save(User user);
    void  update(User user);
    void delete(User user);
    List<User> getAll();
}

