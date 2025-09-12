package com.example.frontend.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("")
public class MainView extends VerticalLayout {
    private final UserForm form;
    private final UserActionsHandler handler;

    public MainView(UserActionsHandler handler, UserForm form) {
        this.form = form;
        this.handler = handler;

        form.setupUI();
        handler.setupEventListeners();

        add(form);
    }
}
