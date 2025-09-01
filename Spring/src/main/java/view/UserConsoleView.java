package view;

import model.User;

import java.util.List;

public class UserConsoleView implements UserView {

    @Override
    public void showUsers(List<User> users) {
        System.out.println("Users:");
        for (User user : users) {
            System.out.println(user.getId() + ": " + user.getName());
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
