package com.example.frontend.ui;

import com.example.core.entity.User;
import com.example.frontend.view.UserPopupView;
import com.example.frontend.ui.UserForm;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

@Service
public class UserActionsHandler {

    private final UserPopupView userConsoleView = new UserPopupView();
    private final UserForm form;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${users.path}")
    private String BASE_URL;

    public UserActionsHandler(UserForm form) {
        this.form = form;
    }

    public void setupEventListeners() {
        form.addButton.addClickListener(e -> addUser());
        form.updateButton.addClickListener(e -> updateUser());
        form.deleteButton.addClickListener(e -> deleteUser());
        form.findUser.addClickListener(e -> findUser());
        form.findAllUsers.addClickListener(e -> findAllUsers());
    }

    private void findAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL, User[].class);
        List<User> users = Arrays.asList(response.getBody());
        userConsoleView.showUsers(users, form.output);
    }

    private void findUser() {
        Long userId = getUserId();
        if (userId == null) return;

        User user = restTemplate.getForObject(BASE_URL + "/" + userId, User.class);
        if (user != null) {
            userConsoleView.showUsers(Arrays.asList(user), form.output);
        } else {
            form.output.setValue("User not found.");
        }
    }

    private void addUser() {
        if (!validateFields()) return;

        User user = new User();
        form.binder.writeBeanIfValid(user);
        User createdUser = restTemplate.postForObject(BASE_URL, user, User.class);
        userConsoleView.showMessage("User added");
        userConsoleView.showUsers(Arrays.asList(createdUser), form.output);
        clearFields();
    }

    private void updateUser() {
        Long userId = getUserId();
        if (userId == null || !validateFields()) return;

        User user = new User();
        form.binder.writeBeanIfValid(user);
        user.setId(userId);
        restTemplate.put(BASE_URL + "/" + userId, user);
        userConsoleView.showMessage("User updated");
        userConsoleView.showUsers(Arrays.asList(user), form.output);
        clearFields();
    }

    private void deleteUser() {
        Long userId = getUserId();
        if (userId == null) return;

        restTemplate.delete(BASE_URL + "/" + userId);
        userConsoleView.showMessage("User deleted");
        form.output.clear();
        clearFields();
    }

    private Long getUserId() {
        try {
            Long id = Long.parseLong(form.id.getValue());
            if (id < 1) {
                Notification.show("ID must be positive", 2000, Notification.Position.BOTTOM_CENTER);
                return null;
            }
            return id;
        } catch (NumberFormatException e) {
            Notification.show("Invalid ID", 2000, Notification.Position.BOTTOM_CENTER);
            return null;
        }
    }

    private boolean validateFields() {
        if (form.name.isEmpty() || form.email.isEmpty()) {
            Notification.show("Fill in all fields", 2000, Notification.Position.BOTTOM_CENTER);
            return false;
        }
        return true;
    }

    private void clearFields() {
        form.id.clear();
        form.name.clear();
        form.email.clear();
    }
}
