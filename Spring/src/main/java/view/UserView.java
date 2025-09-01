package view;

import model.User;
import java.util.List;

public interface UserView {
    void showUsers(List<User> users);
    void showMessage(String message);
}
