package com.example.backend.service;

import com.example.share.interfaces.dto.UserDto;
import com.example.share.interfaces.interfaces.UserDao;
import com.example.core.entity.User;
import com.example.share.interfaces.interfaces.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPresenter  {

    private final UserDao userDao;


    public User addUser(UserDto userDto) {
        if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        User user = new User(userDto.getName(), userDto.getEmail(), userDto.getPassword());
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
