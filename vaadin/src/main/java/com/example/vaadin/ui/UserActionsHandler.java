package com.example.vaadin.ui;

import com.example.vaadin.model.User;
import com.example.vaadin.service.UserPresenter;
import com.example.vaadin.view.UserPopupView;
import com.vaadin.flow.component.notification.Notification;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserActionsHandler {
    private UserPopupView userConsoleView = new UserPopupView();
    private UserPresenter userPresenter;
    private final UserForm form;

    public UserActionsHandler(UserPresenter userPresenter, UserForm form) {
        userPresenter.setView(userConsoleView);
        this.userPresenter = userPresenter;
        this.form = form;
    }

    public void setupEventListeners() {
        form.addButton.addClickListener(e -> addUser(userPresenter));
        form.updateButton.addClickListener(e -> updateUser(userPresenter));
        form.deleteButton.addClickListener(e -> deleteUser(userPresenter));
        form.findUser.addClickListener(e -> findUser(userPresenter));
        form.findAllUsers.addClickListener(e -> findAllUsers(userPresenter));
    }

    private void findAllUsers(UserPresenter userPresenter) {
        userPresenter.showAllUsers(form.output);
    }

    private void findUser(UserPresenter userPresenter) {
        if (emptyUserId()) return;
        Long userId = getUserId();
        User user = userPresenter.showUserById(userId);
        outputSetValue(user);
    }

    private void outputSetValue(User user) {
        form.output.setValue("User: \nName: " + user.getName() + "\nEmail: " + user.getEmail());
    }

    private void addUser(UserPresenter userPresenter) {
        if (validateFields()) {
            User user = new User();
            form.binder.writeBeanIfValid(user);
            userPresenter.addUser(user);
            outputSetValue(user);
            clearFields();
        }
    }

    private void updateUser(UserPresenter userPresenter) {
        if (emptyUserId()) return;
        if (validateFields()) {
            User user = new User();
            form.binder.writeBeanIfValid(user);
            userPresenter.updateUser(user);
            outputSetValue(user);
            clearFields();
        }
    }

    private void deleteUser(UserPresenter userPresenter) {
        if (emptyUserId()) return;
        Long userId = getUserId();
        userPresenter.deleteUser(userId);
        form.output.setValue("User deleted");
        clearFields();
    }

    @Nullable
    private Long getUserId() {
        Long userId;
        try {
            userId = Long.parseLong(form.id.getValue());
            if (userId < 1) {
                Notification.show("ID must be a positive number for deletion.", 3000, Notification.Position.BOTTOM_CENTER);
                return null;
            }
        } catch (NumberFormatException e) {
            Notification.show("ID must be a valid number.", 3000, Notification.Position.BOTTOM_CENTER);
            return null;
        }
        return userId;
    }

    private boolean emptyUserId() {
        if (form.id.isEmpty()) {
            Notification.show("Please enter user details", 3000, Notification.Position.BOTTOM_CENTER);
            return true;
        }
        return false;
    }


    private boolean validateFields() {
        if (form.name.isEmpty() || form.email.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.BOTTOM_CENTER);
            return false;
        }
        return true;
    }

    private void clearFields() {
        form.name.clear();
        form.name.setInvalid(false);
        form.name.setErrorMessage(null);

        form.email.clear();
        form.email.setInvalid(false);
        form.email.setErrorMessage(null);

        form.id.clear();
        form.id.setInvalid(false);
        form.id.setErrorMessage(null);
    }
}
