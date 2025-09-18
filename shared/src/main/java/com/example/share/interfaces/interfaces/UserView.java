package com.example.share.interfaces.interfaces;

import com.example.share.interfaces.dto.UserDto;
import com.vaadin.flow.component.textfield.TextArea;
import com.example.core.entity.User;
import java.util.List;

public interface UserView {
    void showUsers(List<UserDto> users, TextArea output);

    void showMessage(String message);
}
