package com.example.vaadin.view;

import com.example.vaadin.model.User;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.List;

public class UserPopupView implements UserView {

    @Override
    public void showUsers(List<User> users, TextArea output) {
        if (users == null || users.isEmpty()) {
            output.setValue("No users found.");
            return;
        }

        StringBuilder builder = new StringBuilder("Users:\n");
        for (User user : users) {
            builder.append("ID: ").append(user.getId())
                    .append(",\n Name: ").append(user.getName())
                    .append(",\n Email: ").append(user.getEmail())
                    .append("\n");
        }
        Notification.show("All Users");
        output.setValue(builder.toString());
    }

    @Override
    public void showMessage(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}
