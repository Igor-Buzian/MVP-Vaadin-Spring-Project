package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.vaadin.flow.component.textfield.TextArea;
import com.example.core.dao.UserDao;
import com.example.core.entity.User;
import com.example.share.interfaces.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPresenter  {

    private final UserDao userDao;

    private UserView view;

    public void setView(UserView view) {
        this.view = view;
    }

    public void addUser(UserDto userDto) {
        if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            view.showMessage("Name cannot be empty");
            return;
        }
        User user = new User(userDto.getName(), userDto.getEmail());
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


    public void updateUser(UserDto userDto) {
        Optional<User> currentUser = userDao.getById(userDto.getId());
        if (currentUser.isPresent()) {
            User u = currentUser.get();
            u.setEmail(userDto.getEmail());
            u.setName(userDto.getName());
            userDao.update(u);
            view.showMessage("User updated");
        } else {
            view.showMessage("User not found");
        }
    }

   /* public void showAllUsers(TextArea output) {
        List<User> users = userDao.getAll();
        view.showUsers(users, output);
    }
*/
   public List<User> showAllUsers() {
       return userDao.getAll();
   }

    public User showUserById(Long id) {
        Optional<User> user = userDao.getById(id);
        return user.get();
    }
}
