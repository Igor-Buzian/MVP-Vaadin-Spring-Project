package com.example.backend.controller;

import com.example.share.interfaces.dto.UserDto;
import com.example.backend.service.UserPresenter;
import com.example.core.entity.User;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        try {
            User savedUser = userPresenter.addUser(userDto);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid user data: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
      try {
          List<User> users = userPresenter.showAllUsers();
          List<UserDto> userDtos = users.stream()
                  .map(user -> new UserDto(user.getId(), user.getEmail(), user.getName()))
                  .toList();
          return ResponseEntity.ok(userDtos);
      } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
      }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        try {
            User updatedUser = userPresenter.updateUser(userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userPresenter.showUserById(id);
            UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getName());
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        try {
            User deletedUser = userPresenter.deleteUser(id);
            return ResponseEntity.ok(deletedUser);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
