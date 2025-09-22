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

    public void setupEventListeners() {
       // registerForm.registerButton.addClickListener(e -> register());
    }

    private void register() {
        if (ValidationLogic()) return;
        sendRegistration();
    }

    private void sendRegistration() {
        try {
            RegisterDtoValues userDto = new RegisterDtoValues();
            userDto.setUsername(registerForm.name.getValue());
            userDto.setEmail(registerForm.email.getValue());
            userDto.setPassword(registerForm.password.getValue());
           // userDto.setCaptchaResponse(registerForm.captchaResponse);

            template.postForEntity(BASE_URL, userDto, Void.class);
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
