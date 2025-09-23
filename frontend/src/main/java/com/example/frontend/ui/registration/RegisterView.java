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

@Route("register")
public class RegisterView extends VerticalLayout {

    public RegisterView(RegisterForm form, RegistrationActionsHandler actionsHandler) {
        form.setupUI();
        actionsHandler.setupEventListeners();

        add(form);
    }
}

