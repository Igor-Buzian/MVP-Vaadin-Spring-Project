package com.example.frontend.ui.admin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("admin")
public class AdminView extends VerticalLayout {
    private final UserForm form;
    private final UserActionsHandler handler;

    public AdminView(UserActionsHandler handler, UserForm form) {
        this.form = form;
        this.handler = handler;

        form.setupUI();
        handler.setupEventListeners();

        add(form);
    }
}
