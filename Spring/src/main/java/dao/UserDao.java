package dao;

import model.User;
import java.util.List;

public interface UserDao {
    User getById(long id);
    void save(User user);
    void  update(User user);
    void delete(User user);
    List<User> getAll();
}
