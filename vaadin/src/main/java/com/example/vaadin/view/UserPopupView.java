package com.example.vaadin.view;

import com.example.vaadin.model.User;
import com.vaadin.flow.component.notification.Notification;

import java.util.List;

public class UserPopupView implements UserView {

    @Override
    public void showUsers(List<User> users) {
        StringBuilder builder = new StringBuilder("Users:\n");
        for (User user : users) {
            builder.append("ID: ").append(user.getId())
                    .append(", Name: ").append(user.getName())
                    .append(", Email: ").append(user.getEmail()).append("\n");
        }

        Notification.show(builder.toString(), 5000, Notification.Position.MIDDLE);
    }


    @Override
    public void showMessage(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}
