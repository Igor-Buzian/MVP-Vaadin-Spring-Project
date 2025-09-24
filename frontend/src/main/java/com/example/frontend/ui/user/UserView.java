package com.example.frontend.ui.user;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("User")
public class UserView extends VerticalLayout {
    public UserView(){
        add(new H3("User Panel"));
    }
}
