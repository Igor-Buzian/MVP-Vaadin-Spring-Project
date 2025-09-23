package com.example.frontend.ui.registration;

import com.example.frontend.view.PopupView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.vaadin.addons.googlerecaptcha.v2.RecaptchaComponentv2;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:MyFrontendApp.properties")
public class RegisterForm extends VerticalLayout{
    private final PopupView view;

    TextField name = new TextField("Name");
    EmailField email = new EmailField("Email");
    PasswordField password = new PasswordField("Password");
    PasswordField passwordConfirm = new PasswordField("Confirm Password");
    Button registerButton = new Button("Register");
    Button confirmButton = new Button( "Confirm");

    @Value("${recaptcha.site_key}")
    String recaptchaDataSiteKey;

    @Value("${recaptcha.secret}")
    String recaptchaSecretKey;

    RecaptchaComponentv2 recaptcha;

    public void setupUI() {
        recaptcha = new RecaptchaComponentv2(recaptchaDataSiteKey, recaptchaSecretKey);
        H2 header = new H2("Register");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(name, email, password, passwordConfirm, recaptcha);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        formLayout.setSizeFull();

        add(header, formLayout, registerButton);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

}

