package com.example.share.interfaces;

import com.vaadin.flow.component.textfield.TextArea;
import com.example.core.entity.User;
import java.util.List;

public interface UserView {
    void showUsers(List<User> users, TextArea output);

    void showMessage(String message);
}
