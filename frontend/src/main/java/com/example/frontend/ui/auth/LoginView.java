package com.example.frontend.ui.auth;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView(LoginForm form, LoginActionHandler actionsHandler) {
        form.setupUI();
        actionsHandler.setupEventListeners();

        add(form);
    }
}

