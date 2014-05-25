package frontend;

/**
 * Created by elvira on 09.03.14.
 */
public class Resources {

    public interface URL{
        String LOGIN = "/login";
        String REGISTER = "/register";
        String AUTHFORM = "/index";
        String TIMER_PAGE = "/timer";
        String REGISTRATION_FORM = "/registration";
        int PORT = 8081;
        int TEST_PORT = 8082;
        String DB = "jdbc:mysql://localhost:3306/Ejava";
        String TEST_DB = "jdbc:mysql://localhost:3306/etests";

    }

    public interface Template{
        String INDEX = "index.tml";
        String REGISTRATION = "registration.tml";
        String TIMER = "timer.tml";
    }

    public interface InfoMessage{
        String WRONG_PASSWORD = "Wrong password!";
        String EMPTY_FIELDS = "All fields must be filled!";
        String DUPLICATE_LOGIN = "Choose another login!";
        String NOT_REGISTERED = "Not registered!";
        String DB_UNREACHABLE = "DB unreachable";
    }
}
