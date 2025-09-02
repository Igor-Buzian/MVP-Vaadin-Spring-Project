package com.example.vaadin.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("")
public class MainView extends VerticalLayout {
    private final UserForm form;
    private final UserActionsHandler handler;

    @Autowired
    public MainView(UserActionsHandler handler, UserForm form) {
        this.form = form;
        this.handler = handler;

        form.setupUI();
        handler.setupEventListeners();

        add(form);
    }
}
