package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import com.example.backend.service.UserPresenter;
import com.example.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserPresenter userPresenter;

    public UserController(UserPresenter userPresenter) {
        this.userPresenter = userPresenter;
    }

    @PostMapping
    public void saveUser(@RequestBody UserDto userDto) {
        userPresenter.addUser(userDto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userPresenter.showAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userPresenter.showUserById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody UserDto userDto) {
        userPresenter.updateUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userPresenter.deleteUser(id);
    }
}
