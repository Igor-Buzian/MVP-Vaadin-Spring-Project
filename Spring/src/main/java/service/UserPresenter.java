package service;

import dao.UserDao;
import model.User;
import view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPresenter {

    @Autowired
    private UserDao userDao;

    private UserView view;

    public UserPresenter() {
    }

    public void setView(UserView view) {
        this.view = view;
    }

    public void addUser(String email, String name) {
        if (name == null || name.trim().isEmpty()) {
            view.showMessage("Name cannot be empty");
            return;
        }
        User user = new User(email, name);
        userDao.save(user);
        view.showMessage("User saved");
    }

    public void deleteUser(long id) {
        User user = userDao.getById(id);
        userDao.delete(user);
        view.showMessage("User deleted");
    }

    public void updateUser(long id, String newEmail, String newName) {
        User user = userDao.getById(id);
        user.setEmail(newEmail);
        user.setName(newName);
        userDao.update(user);
        view.showMessage("User updated");
    }

    public void showAllUsers() {
        List<User> users = userDao.getAll();
        view.showUsers(users);
    }
}
