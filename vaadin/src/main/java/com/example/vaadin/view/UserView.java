package com.example.vaadin.view;


import com.example.vaadin.model.User;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.List;

public interface UserView {
    void showUsers(List<User> users, TextArea output);

    void showMessage(String message);
}
