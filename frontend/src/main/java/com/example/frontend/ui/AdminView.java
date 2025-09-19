package com.example.frontend.ui;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Admin")
public class AdminView extends VerticalLayout {
    public AdminView(){
        add(new H3("Admin Panel"));
    }
}
