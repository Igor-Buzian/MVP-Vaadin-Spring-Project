package com.example.frontend.ui.registration;

import com.example.share.interfaces.dto.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
public class RegisterForm extends VerticalLayout{

    TextField name = new TextField("Name");
    EmailField email = new EmailField("Email");
    PasswordField password = new PasswordField("Password");
    PasswordField passwordConfirm = new PasswordField("Confirm Password");

    Button registerButton = new Button("Register");

    public void setupUI() {
        H2 header = new H2("Register");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(name, email, password, passwordConfirm);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        add(header, formLayout, registerButton);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

}

