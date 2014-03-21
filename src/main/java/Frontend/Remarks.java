package Frontend;

/**
 * Created by Elvira on 11.03.14.
 */
public class Remarks {
    public static class Url {
        final static public String AUTHORIZATION = "/authorization";
        final static public String REGISTRATION = "/registration";
        final static public String SESSION = "/idUser";
        final static public String INDEX = "/";
    }

    public static class Page {
        final static public String INDEX = "/index.html";
        final static public String REGISTRATION = "/registration.tml";
        final static public String AUTHORIZATION = "/authorization.tml";
        final static public String SESSION = "/user.tml";
    }

    public static class Warnings {
        final static public String FIELDS = "Empty fields";
        final static public String REGISTRATION = "User was added";
        final static public String FAIL = "login or password is wrong";
        final static public String USER = "User exists";

    }

    final static public String REFRESH_TIME = "5000";
}