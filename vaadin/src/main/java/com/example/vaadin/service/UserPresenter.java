package com.example.vaadin.service;

import com.example.vaadin.dao.UserDao;
import com.example.vaadin.model.User;
import com.example.vaadin.view.UserView;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPresenter {

    private final UserDao userDao;

    private UserView view;

    public void setView(UserView view) {
        this.view = view;
    }

    public void addUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            view.showMessage("Name cannot be empty");
            return;
        }
        userDao.save(user);
        view.showMessage("User saved");
    }


    public void deleteUser(long id) {
        Optional<User> user = userDao.getById(id);
        if (user.isPresent()) {
            userDao.delete(user.get());
            view.showMessage("User deleted");
        } else {
            view.showMessage("User with id " + id + " not found");
        }
    }


    public void updateUser(User user) {
        Optional<User> currentUser = userDao.getById(user.getId());
        if (currentUser.isPresent()) {
            User u = currentUser.get();
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            userDao.update(u);
            view.showMessage("User updated");
        } else {
            view.showMessage("User not found");
        }
    }

    public void showAllUsers() {
        List<User> users = userDao.getAll();
        view.showUsers(users);
    }

    public User showUserById(Long id){
       Optional<User> user = userDao.getById(id);
       return user.get();
    }
}
