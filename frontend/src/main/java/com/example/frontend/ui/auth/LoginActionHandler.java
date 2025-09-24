package com.example.frontend.ui.auth;

import com.example.frontend.view.PopupView;
import com.example.share.interfaces.dto.JwtRequest;
import com.vaadin.flow.component.UI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoginActionHandler {
    @Value("${auth.path}")
    private String BASE_URL;

    private final LoginForm form;
    private final PopupView view;
    private final RestTemplate template = new RestTemplate();

    private String responseToken;

    public String getResponseToken() {
        return responseToken;
    }

    public void setResponseToken(String responseToken) {
        this.responseToken = responseToken;
    }

    public void setupEventListeners() {
        form.loginButton.addClickListener(e -> login());
    }

    private void login() {
        if (ValidationLogic()) return;
        setupListeners();
    }

    private boolean ValidationLogic() {
        if (form.email.isEmpty() || form.password.isEmpty()) {
            view.showMessage("Please fill all fields");
            return true;
        }

        return false;
    }

    private void setupListeners() {
        UI.getCurrent().getPage().executeJs("return grecaptcha.getResponse();")
                .then(String.class, token -> {
                    this.setResponseToken(token);

                    if (token == null || token.isEmpty()) {
                        view.showMessage("Invalid captcha!");
                    } else {
                        view.showMessage("Success captcha!");
                        sendLogin();
                    }
                });
    }

    private void sendLogin() {
        try {
            JwtRequest request = new JwtRequest();
            request.setEmail(form.email.getValue());
            request.setPassword(form.password.getValue());
            request.setCaptchaResponse(this.responseToken);

            ResponseEntity<Void> response = template.postForEntity(BASE_URL, request, Void.class);

            if (response.getStatusCode().is3xxRedirection()) {
                String location = response.getHeaders().getLocation().toString();
                UI.getCurrent().navigate(location);
            }

        } catch (Exception ex) {
            view.showMessage("Registration failed: " + ex.getMessage());
        }
    }
}
