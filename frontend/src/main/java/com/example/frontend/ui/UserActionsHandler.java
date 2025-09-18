package com.example.frontend.ui;

import com.example.frontend.view.PopupView;
import com.example.share.interfaces.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class UserActionsHandler {

    private final PopupView userConsoleView = new PopupView();
    private final UserForm form;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${users.path}")
    private String BASE_URL;

    public UserActionsHandler(UserForm form) {
        this.form = form;
    }

    public void setupEventListeners() {
        form.addButton.addClickListener(e -> addUser());
        form.updateButton.addClickListener(e -> updateUser());
        form.deleteButton.addClickListener(e -> deleteUser());
        form.findUser.addClickListener(e -> findUser());
        form.findAllUsers.addClickListener(e -> findAllUsers());
    }

    private void findAllUsers() {
        try {
            ResponseEntity<UserDto[]> response = restTemplate.getForEntity(BASE_URL, UserDto[].class);
            List<UserDto> users = Arrays.asList(response.getBody());
            userConsoleView.showUsers(users, form.output);
        } catch (Exception e) {
            userConsoleView.showMessage("Failed to load users");
        }
    }

    private void findUser() {
        Long userId = getUserId();
        if (userId == null) return;

        try {
           UserDto userDto = restTemplate.getForObject(BASE_URL + "/" + userId, UserDto.class);
            userConsoleView.showMessage("Found user");
            form.output.setValue(userDto.toString());
        } catch (HttpClientErrorException.NotFound e) {
            form.output.setValue("User not found.");
        } catch (Exception e) {
            userConsoleView.showMessage("Failed to load user");
        }
    }

    private void addUser() {
        if (!validateFields()) return;

        UserDto userDto = new UserDto();
        userDto.setId(21l);
        userDto.setName(form.name.getValue());
        userDto.setEmail(form.email.getValue());

        restTemplate.postForEntity(BASE_URL, userDto, Void.class);
        userConsoleView.showMessage("User added");
        findAllUsers();
        clearFields();

    }

    private void updateUser() {
        Long userId = getUserId();
        if (userId == null || !validateFields()) return;

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(form.name.getValue());
        userDto.setEmail(form.email.getValue());

        try {
            restTemplate.put(BASE_URL + "/" + userId, userDto);
            findAllUsers();
            userConsoleView.showMessage("User updated");
            clearFields();
        } catch (Exception e) {
            userConsoleView.showMessage("Failed to update user");
        }
    }

    private void deleteUser() {
        Long userId = getUserId();
        if (userId == null) return;
        try {
            restTemplate.delete(BASE_URL + "/" + userId, Long.class);
            form.output.clear();
            clearFields();
            userConsoleView.showMessage("User deleted");
        } catch (Exception e) {
            userConsoleView.showMessage("Failed to delete user");
        }
    }

    private Long getUserId() {
        try {
            Long id = Long.parseLong(form.id.getValue());
            if (id < 1) {
                userConsoleView.showMessage("ID must be positive");
                return null;
            }
            return id;
        } catch (NumberFormatException e) {
            userConsoleView.showMessage("Invalid ID");
            return null;
        }
    }

    private boolean validateFields() {
        if (form.name.isEmpty() || form.email.isEmpty()) {
            userConsoleView.showMessage("Fill in all fields");
            return false;
        }
        return true;
    }

    private void clearFields() {
        form.name.clear();
        form.name.setInvalid(false);
        form.name.setErrorMessage(null);

        form.email.clear();
        form.email.setInvalid(false);
        form.email.setErrorMessage(null);

        form.id.clear();
        form.id.setInvalid(false);
        form.id.setErrorMessage(null);
    }
}
