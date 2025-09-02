package com.example.vaadin.view;

import com.example.vaadin.model.User;
import com.example.vaadin.service.UserPresenter;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.jetbrains.annotations.NotNull;
import com.vaadin.flow.data.binder.Binder;
import org.jetbrains.annotations.Nullable;


@Route("")
public class MainView extends VerticalLayout {

    TextArea id = new TextArea("User id");
    TextArea name = new TextArea("Name");
    TextArea email = new TextArea("Email");
    TextArea output = new TextArea("Output");

    private Button addButton = new Button("Add", new Icon(VaadinIcon.PLUS));
    private Button updateButton = new Button("Update", new Icon(VaadinIcon.EDIT));
    private Button deleteButton = new Button("Delete", new Icon(VaadinIcon.TRASH));
    private Button findUser = new Button("Find user", new Icon(VaadinIcon.USER));
    private Button findAllUsers = new Button("Find Users", new Icon(VaadinIcon.USERS));

    private Binder<User> binder = new Binder<>(User.class);
    private UserPopupView userConsoleView = new UserPopupView();

    public MainView(UserPresenter userPresenter) {
        userPresenter.setView(userConsoleView);

        setupUI();
        setupEventListeners(userPresenter);
    }

    private void setupUI() {
        H3 header = headerInterface();

        idLogic();
        nameLogic();
        emailLogic();
        outputInfo();
        buttonsVisual();

        VerticalLayout formWrapper = userForm();
        VerticalLayout outputWrapper = outputForm();
        HorizontalLayout mainLayout = getMainLayout(formWrapper, outputWrapper);

        HorizontalLayout managementButtonsLayout = managementButtonsForm();
        HorizontalLayout searchButtonsLayout = searchButtons();

        mainVisualInterfaces(header, mainLayout, managementButtonsLayout, searchButtonsLayout);
    }

    private HorizontalLayout searchButtons() {
        HorizontalLayout buttonsWrapper = new HorizontalLayout(findUser, findAllUsers);
        buttonsWrapper.setWidth("300px");
        buttonsWrapper.setPadding(true);
        buttonsWrapper.setSpacing(true);
        buttonsWrapper.setAlignItems(Alignment.CENTER);
        buttonsWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        return buttonsWrapper;
    }

    private void mainVisualInterfaces(H3 header, HorizontalLayout mainLayout, HorizontalLayout managementButtonsLayout,  HorizontalLayout searchButtonsLayout) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        getStyle().set("background-color", "#e9ecef");
        getStyle().set("padding", "40px");

        add(header, mainLayout, managementButtonsLayout, searchButtonsLayout);
    }

    @NotNull
    private static HorizontalLayout getMainLayout(VerticalLayout formWrapper, VerticalLayout outputWrapper) {
        HorizontalLayout mainLayout = new HorizontalLayout(formWrapper, outputWrapper);
        mainLayout.setSpacing(true);
        mainLayout.setPadding(true);
        mainLayout.setAlignItems(Alignment.CENTER);
        return mainLayout;
    }

    @NotNull
    private VerticalLayout outputForm() {
        VerticalLayout outputWrapper = new VerticalLayout(output);
        outputWrapper.setWidth("400px");
        outputWrapper.setPadding(true);
        return outputWrapper;
    }

    @NotNull
    private HorizontalLayout managementButtonsForm() {
        HorizontalLayout buttonsWrapper = new HorizontalLayout(addButton, updateButton, deleteButton);
        buttonsWrapper.setWidth("300px");
        buttonsWrapper.setPadding(true);
        buttonsWrapper.setSpacing(true);
        buttonsWrapper.setAlignItems(Alignment.CENTER);
        buttonsWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        return buttonsWrapper;
    }

    @NotNull
    private VerticalLayout userForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("350px");
        formLayout.add(id, name, email);
        formLayout.setColspan(id, 1);
        formLayout.setColspan(name, 1);
        formLayout.setColspan(email, 1);

        VerticalLayout formWrapper = new VerticalLayout(formLayout);
        formWrapper.setPadding(false);
        formWrapper.setSpacing(false);
        formWrapper.setWidth(null);
        return formWrapper;
    }

    private void emailLogic() {
        email.setPlaceholder("Enter your email address");
        email.setWidthFull();
        email.setHeight(60, Unit.PIXELS);
        binder.forField(email)
                .asRequired("Email can't be empty!")
                .withValidator(email -> email.contains("@"), "int emait cane be '@'")
                .bind(User::getEmail, User::setEmail);
    }

    private void nameLogic() {
        name.setPlaceholder("Enter your full name");
        name.setWidthFull();
        name.setHeight(60, Unit.PIXELS);
        binder.forField(name)
                .asRequired("Name can't be empty!")
                .bind(User::getName, User::setName);
    }

    private void idLogic() {
        id.setPlaceholder("Enter your user id (not required)");
        id.setWidthFull();
        id.setHeight(60, Unit.PIXELS);

        binder.forField(id)
                .withConverter(
                        value -> {
                            if (value == null || value.trim().isEmpty()) {
                                return null; // Для addUser это допустимо
                            }
                            try {
                                return Long.parseLong(value.trim());
                            } catch (NumberFormatException e) {
                                throw new RuntimeException("ID must be a number");
                            }
                        },
                        object -> object == null ? "" : object.toString()
                )
                .bind(User::getId, User::setId);
    }

    private void buttonsVisual() {
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        findUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_TERTIARY);
        findAllUsers.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_TERTIARY);

        addButton.setWidthFull();
        updateButton.setWidthFull();
        deleteButton.setWidthFull();
        findUser.setWidthFull();
        findAllUsers.setWidthFull();

        addButton.setHeight(55, Unit.PIXELS);
        updateButton.setHeight(55, Unit.PIXELS);
        deleteButton.setHeight(55, Unit.PIXELS);
        findUser.setHeight(55, Unit.PIXELS);
        findAllUsers.setHeight(55, Unit.PIXELS);
    }

    private void outputInfo() {
        output.setReadOnly(true);
        output.setWidthFull();
        output.setHeight(220, Unit.PIXELS);
        output.setValue("User info will appear here...");
        output.getStyle()
                .set("border", "2px solid #4CAF50")
                .set("border-radius", "8px")
                .set("background-color", "#f9fff9")
                .set("padding", "10px")
                .set("box-sizing", "border-box");
    }

    @NotNull
    private static H3 headerInterface() {
        H3 header = new H3("User Management");
        header.getStyle()
                .set("font-weight", "700")
                .set("font-size", "2rem")
                .set("margin-bottom", "30px")
                .set("color", "#333");
        return header;
    }

    private void setupEventListeners(UserPresenter userPresenter) {
        addButton.addClickListener(e -> addUser(userPresenter));
        updateButton.addClickListener(e -> updateUser(userPresenter));
        deleteButton.addClickListener(e -> deleteUser(userPresenter));
        findUser.addClickListener(e ->findUser(userPresenter));
        findAllUsers.addClickListener(e ->findAllUsers(userPresenter));
    }

    private void findAllUsers(UserPresenter userPresenter) {
        userPresenter.showAllUsers(output);
    }

    private void findUser(UserPresenter userPresenter) {
        if(emptyUserId())return;
        Long userId = getUserId();
        User user = userPresenter.showUserById(userId);
        output.setValue("User: \nName: " + user.getName() + "\nEmail: " + user.getEmail());
    }

    private void addUser(UserPresenter userPresenter) {
        if (validateFields()) {
            User user = new User();
            binder.writeBeanIfValid(user);
            userPresenter.addUser(user);
            output.setValue("User: \nName: " + name.getValue() + "\nEmail: " + email.getValue());
            clearFields();
        }
    }

    private void updateUser(UserPresenter userPresenter) {
        if (emptyUserId()) return;
        if (validateFields()) {
            User user = new User();
            binder.writeBeanIfValid(user);
            userPresenter.updateUser(user);
            output.setValue("User updated:\nName: " + name.getValue() + "\nEmail: " + email.getValue());
            clearFields();
        }
    }

    private void deleteUser(UserPresenter userPresenter) {
        if (emptyUserId()) return;
        Long userId = getUserId();
        userPresenter.deleteUser(userId);
        output.setValue("User deleted:\nName: " + name.getValue() + "\nEmail: " + email.getValue());
        clearFields();
    }

    @Nullable
    private Long getUserId() {
        Long userId;
        try {
            userId = Long.parseLong(id.getValue());
            if (userId < 1) {
                Notification.show("ID must be a positive number for deletion.", 3000, Notification.Position.MIDDLE);
                return null;
            }
        } catch (NumberFormatException e) {
            Notification.show("ID must be a valid number.", 3000, Notification.Position.MIDDLE);
            return null;
        }
        return userId;
    }

    private boolean emptyUserId() {
        if (id.isEmpty()) {
            Notification.show("Please enter user details", 3000, Notification.Position.MIDDLE);
            return true;
        }
        return false;
    }


    private boolean validateFields() {
        if (name.isEmpty() || email.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            return false;
        }
        return true;
    }

    private void clearFields() {
        name.clear();
        email.clear();
        id.clear();

        name.setInvalid(false);
        name.setErrorMessage(null);

        email.setInvalid(false);
        email.setErrorMessage(null);

        id.setInvalid(false);
        id.setErrorMessage(null);
    }
}
