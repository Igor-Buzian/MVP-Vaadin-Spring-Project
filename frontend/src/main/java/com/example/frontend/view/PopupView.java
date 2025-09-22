package com.example.frontend.view;


import com.example.share.interfaces.dto.UserDto;
import com.example.share.interfaces.interfaces.UserView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.stereotype.Component;


import java.util.List;
@Component
public class PopupView implements UserView {

    @Override
    public void showUsers(List<UserDto> users, TextArea output) {
        if (users == null || users.isEmpty()) {
            output.setValue("No users found.");
            return;
        }

        StringBuilder builder = new StringBuilder("Users:\n");
        for (UserDto user : users) {
            builder.append("ID: ").append(user.getId())
                    .append(",\n Name: ").append(user.getName())
                    .append(",\n Email: ").append(user.getEmail())
                    .append("\n");
        }
        Notification.show("All Users", 3000, Notification.Position.BOTTOM_CENTER);
        output.setValue(builder.toString());
    }

    @Override
    public void showMessage(String message) {
        Notification.show(message, 3000, Notification.Position.BOTTOM_CENTER);
    }
}
