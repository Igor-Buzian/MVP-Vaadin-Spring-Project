package com.example.frontend.ui.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginForm extends VerticalLayout {


    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");

    private Button loginButton = new Button("Login");

    public LoginForm() {
        H2 header = new H2("Login");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(email, password);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        //   loginButton.addClickListener(e -> login());

        add(header, formLayout, loginButton);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    private void login() {
        if (email.isEmpty() || password.isEmpty()) {
            Notification.show("Please fill all fields");
            return;
        }

        // Сформируем JWT запрос (пример упрощённый)
        var jwtRequest = new com.example.share.interfaces.dto.JwtRequest();
        jwtRequest.setEmail(email.getValue());
        jwtRequest.setPassword(password.getValue());

/* try {
            // Можно расширить, передав HttpServletRequest и HttpServletResponse, если нужны куки и редиректы
            var response = authService.authUser(jwtRequest, null, null, null);

            if (response.getStatusCode().is3xxRedirection()) {
                Notification.show("Login successful!");
                getUI().ifPresent(ui -> ui.navigate("dashboard")); // например, главная страница после логина
            } else {
                Notification.show("Login failed");
            }
        } catch (Exception ex) {
            Notification.show("Login failed: " + ex.getMessage());
        }

    }*/
    }
}
