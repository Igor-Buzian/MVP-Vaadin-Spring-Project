package com.example.frontend.ui.registration;

import com.example.frontend.view.PopupView;
import com.example.share.interfaces.dto.RegisterDtoValues;
import com.example.share.interfaces.dto.UserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:MyFrontendApp.properties")
public class RegistrationActionsHandler {
    @Value("${register.path}")
    private String BASE_URL;

    private final PopupView view;
    private final RegisterForm registerForm;
    private final RestTemplate template = new RestTemplate();
    private String responseToken;

    public String getResponseToken() {
        return responseToken;
    }

    public void setResponseToken(String responseToken) {
        this.responseToken = responseToken;
    }

    public void setupEventListeners() {
        //registerForm.recaptcha.addAttachListener(e-> setupListeners());
        registerForm.registerButton.addClickListener(e -> register());
    }

    private void setupListeners() {
        UI.getCurrent().getPage().executeJs("return grecaptcha.getResponse();")
                .then(String.class, token -> {
                    this.setResponseToken(token);

                    if (token == null || token.isEmpty()) {
                        view.showMessage("Invalid captcha!");
                    } else {
                        view.showMessage("Success captcha!");
                        sendRegistration();
                    }
                });
    }


    private void register() {
        if (ValidationLogic()) return;
        setupListeners();
    }

    private void sendRegistration() {
        try {
            RegisterDtoValues dtoValues = new RegisterDtoValues();
            dtoValues.setUsername(registerForm.name.getValue());
            dtoValues.setEmail(registerForm.email.getValue());
            dtoValues.setPassword(registerForm.password.getValue());
            dtoValues.setConfirmPassword(registerForm.passwordConfirm.getValue());
            dtoValues.setCaptchaResponse(this.responseToken);

            template.postForEntity(BASE_URL, dtoValues, RegisterDtoValues.class);
            view.showMessage("Registration successful!");
        } catch (Exception ex) {
            view.showMessage("Registration failed: " + ex.getMessage());
        }
    }

    private boolean ValidationLogic() {
        if (registerForm.name.isEmpty() || registerForm.email.isEmpty() || registerForm.password.isEmpty() || registerForm.passwordConfirm.isEmpty()) {
            view.showMessage("Please fill all fields");
            return true;
        }

        if (!registerForm.password.getValue().equals(registerForm.passwordConfirm.getValue())) {
            view.showMessage("Passwords do not match");
            return true;
        }
        return false;
    }
}
