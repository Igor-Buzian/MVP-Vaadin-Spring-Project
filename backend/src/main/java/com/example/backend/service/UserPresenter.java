package com.example.backend.service;

import com.example.core.interfaces.RoleRepository;
import com.example.core.entity.Role;
import com.example.share.interfaces.dto.UserDto;
import com.example.core.interfaces.UserDao;
import com.example.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserPresenter  {

    private final UserDao userDao;
    private final RoleRepository roleRepository;

    public User addUser(UserDto userDto) {
        if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        Optional<Role> userRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User(userDto.getName(), userDto.getEmail(), userDto.getPassword(),new HashSet<>(Collections.singletonList(userRole.get())));
        userDao.save(user);
        return user;
    }


    public User deleteUser(long id) {
        Optional<User> user = Optional.ofNullable(userDao.getById(id).orElseThrow(() -> new IllegalArgumentException("No one user with this id is not exit, so we cant delete him/his!")));
            userDao.delete(user.get());
            return user.get();
    }

    public User updateUser(UserDto userDto) {
        Optional<User> currentUser = Optional.ofNullable(userDao.getById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("No one user with this id is not exit, so we cant update him/his!")));
            User u = currentUser.get();
            u.setEmail(userDto.getEmail());
            u.setName(userDto.getName());
            userDao.update(u);
            return u;
    }

    public List<User> showAllUsers() {
        return userDao.getAll();
    }

    public User showUserById(Long id) {
        return userDao.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
