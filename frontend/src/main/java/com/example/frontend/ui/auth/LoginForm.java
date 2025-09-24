package com.example.frontend.ui.auth;

import com.example.share.interfaces.dto.JwtRequest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.vaadin.addons.googlerecaptcha.v2.RecaptchaComponentv2;

@Service
@PropertySource("classpath:MyFrontendApp.properties")
public class LoginForm extends VerticalLayout {


     EmailField email = new EmailField("Email");
     PasswordField password = new PasswordField("Password");

     Button loginButton = new Button("Login");

    @Value("${recaptcha.site_key}")
    String recaptchaDataSiteKey;

    @Value("${recaptcha.secret}")
    String recaptchaSecretKey;

    RecaptchaComponentv2 recaptcha;

    public void setupUI() {
        recaptcha = new RecaptchaComponentv2(recaptchaDataSiteKey, recaptchaSecretKey);

        H2 header = new H2("Login");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(email, password, recaptcha);
        //formLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        add(header, formLayout, loginButton);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }
}
