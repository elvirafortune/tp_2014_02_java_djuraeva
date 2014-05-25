package frontend;

import message.AddressService;
import message.Msg.MessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

/*
 * Created by elvira on 06.03.14.
 */
@RunWith(Parameterized.class)
public class RoutingTest {
    final private HttpServletRequest request = mock(HttpServletRequest.class);
    final private HttpServletResponse response = mock(HttpServletResponse.class);
    final private HttpSession session = mock(HttpSession.class);
    final private MessageService msg = mock(MessageService.class);
    private StringWriter stringWrite = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWrite);
    Frontendl frontend = new Frontendl(msg);
    private String url;
    private String correctAnswer;
    private static final String status = "STATUS";
    private final String login = AccountServiceTest.getRandString(5);

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {Resources.URL.AUTHFORM, "<title>Index</title>"},
                {Resources.URL.REGISTRATION_FORM, "<title>Registration</title>"},
                {Resources.URL.TIMER_PAGE, "UserID: " + status}
        });
    }

    public RoutingTest(String url, String correctAnswer) {
        this.url = url;
        this.correctAnswer = correctAnswer;
    }

    @Before
    public void setUp() throws IOException{
        String sessionID = "session";
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(sessionID);
        frontend.setSessionAuthStatus(sessionID, new UserSession(sessionID, login, mock(AddressService.class)));
        frontend.setUserSessionStatus(sessionID, status);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getPathInfo()).thenReturn(url);
        frontend.doGet(request, response);
        Assert.assertTrue(stringWrite.toString().contains(correctAnswer));
    }
}