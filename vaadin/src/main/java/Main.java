import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserPresenter;
import view.UserConsoleView;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserPresenter presenter = context.getBean(UserPresenter.class);
        UserConsoleView view = new UserConsoleView();

        presenter.setView(view);

        presenter.updateUser(6, "Amilly@gmail.com","Amilly");
        presenter.deleteUser(7);
        presenter.addUser("Alice@gmail.com","Alice");

        presenter.showAllUsers();
    }
}
