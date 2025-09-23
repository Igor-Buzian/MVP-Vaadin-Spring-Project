package com.example.backend.service;

import com.example.core.interfaces.RoleRepository;
import com.example.core.entity.Role;
import com.example.share.interfaces.dto.JwtRequest;
import com.example.share.interfaces.dto.UserDto;
import com.example.core.interfaces.UserDao;
import com.example.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPresenter {
    private final UserDao userDao;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(UserDto userDto) {
        validateUserDto(userDto);

        Set<Role> roles = resolveRoles(userDto.getRoles());

        User user = new User();
        user.setName(userDto.getName().trim());
        user.setEmail(userDto.getEmail().trim());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);

        userDao.save(user);
        return user;
    }

    private Set<Role> resolveRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            return Set.of(defaultRole);
        }

        return roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());
    }

    private void validateUserDto(UserDto userDto) {
        if (userDto.getName() == null || userDto.getName().trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty");
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty())
            throw new IllegalArgumentException("Password cannot be empty");
    }

    public void createUserEntity(User user) {
        userDao.save(user);
    }


    public User updateUser(UserDto userDto) {
        User user = userDao.getById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        validateUpdateUser(userDto, user);

        userDao.update(user);
        return user;
    }

    private void validateUpdateUser(UserDto userDto, User user) {
        if (userDto.getName() != null && !userDto.getName().trim().isEmpty())
            user.setName(userDto.getName().trim());

        if (userDto.getEmail() != null && !userDto.getEmail().trim().isEmpty())
            user.setEmail(userDto.getEmail().trim());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty())
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty())
            user.setRoles(resolveRoles(userDto.getRoles()));
    }

    public User deleteUser(long id) {
        Optional<User> user = Optional.ofNullable(userDao.getById(id).orElseThrow(() -> new IllegalArgumentException("No one user with this id is not exit, so we cant delete him/his!")));
        userDao.delete(user.get());
        return user.get();
    }


    public List<User> showAllUsers() {
        return userDao.getAll();
    }

    public User showUserById(Long id) {
        return userDao.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userDao.getByEmail(email);
        return user.get();
    }

    public boolean existsByEmail(String email) {
        try {
            return userDao.existsByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
