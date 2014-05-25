package frontend;

import frontend.AccountService;
import frontend.Frontendl;
import message.Address;
import message.AddressService;
import message.Msg.MessageService;
import message.Msg.MsgGetUserID;
import message.Msg.MsgRegUser;
import message.Msg.MsgUpdateUserID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/*
 * Created by elvira on 06.03.14.
 */
public class FrontendTest {

    final private HttpServletRequest request = mock(HttpServletRequest.class);
    final private HttpServletResponse response = mock(HttpServletResponse.class);
    final private HttpSession session = mock(HttpSession.class);
    final private MessageService ms = mock(MessageService.class);

    final private AddressService addressService = mock(AddressService.class);
    final private Address accountServiceAddress = new Address();
    final private Address frontendAddress = new Address();

    final private MsgGetUserID msgGetUserID = mock(MsgGetUserID.class);
    final private MsgRegUser msgRegUser = mock(MsgRegUser.class);

    private StringWriter stringWrite = new StringWriter();
    private PrintWriter writer = new PrintWriter(stringWrite);
    private Frontendl toTest = spy(new Frontendl(ms));
    private Frontendl frontend;

    private String url;
    private final Long USER_ID = 5L;
    private String testLogin = AccountServiceTest.getRandString(8);
    private String testPassword = AccountServiceTest.getRandString(8);


    @Before
    public void setUp() throws IOException{
        frontend = new Frontendl(ms);


        when(ms.getAddressService()).thenReturn(addressService);
        when(addressService.getAccountServiceAddress()).thenReturn(accountServiceAddress);

        when(session.getId()).thenReturn(USER_ID.toString());
        when(request.getSession()).thenReturn(session);

        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("login")).thenReturn(testLogin);
        when(request.getParameter("password")).thenReturn(testPassword);

        doReturn(frontendAddress).when(toTest).getAddress();
    }

    @Test
    public void testDoGet() throws Exception {
        Assert.assertTrue(doGetTest.goTest(Resources.URL.AUTHFORM, "Index"));
        Assert.assertTrue(doGetTest.goTest(Resources.URL.REGISTRATION_FORM, "Registration"));
        Assert.assertTrue(doGetTest.goTest(Resources.URL.TIMER_PAGE, "Timer"));
    }

    @Test
    public void testSomeURL() throws Exception {
        url = "/" + AccountServiceTest.getRandString(10);
        when(request.getPathInfo()).thenReturn(url);
        frontend.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/");
    }

    @Test
    public void testTimerPageWithoutSession() throws Exception {
        url = Resources.URL.TIMER_PAGE;
        when(request.getPathInfo()).thenReturn(url);
        frontend.doGet(request, response);
        verify(response, times(1)).sendRedirect(Resources.URL.AUTHFORM);
    }

    //Registration tests

    @Test
    public void testSuccessfulRegistration() throws Exception {
        url = Resources.URL.REGISTER;
        when(request.getPathInfo()).thenReturn(url);
        doReturn(msgRegUser).when(toTest).createMsgRegUser
                (frontendAddress, accountServiceAddress, testLogin, testPassword, USER_ID.toString());
        toTest.doPost(request, response);
        verify(ms, atLeastOnce()).sendMessage(msgRegUser);
        verify(toTest, atLeastOnce()).sendPage(response, Resources.Template.REGISTRATION, "wait");
    }

    @Test
    public void testUnsuccessfulRegistrationWithEmptyFields() throws Exception {
        url = Resources.URL.REGISTER;
        when(request.getPathInfo()).thenReturn(url);
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        frontend.doPost(request, response);
        boolean emptyFields = stringWrite.toString().contains(Resources.InfoMessage.EMPTY_FIELDS);
        Assert.assertTrue(emptyFields);
    }

    //Login tests

    @Test
    public void testLogin() throws Exception {
        url = Resources.URL.LOGIN;
        when(request.getPathInfo()).thenReturn(url);
        doReturn(msgGetUserID).when(toTest).createMsgGetUserID
                (frontendAddress, accountServiceAddress, testLogin, testPassword, USER_ID.toString());
        toTest.doPost(request, response);
        verify(ms, atLeastOnce()).sendMessage(msgGetUserID);
        verify(response, atLeastOnce()).sendRedirect("/timer");
    }

    @Test
    public void testUnsuccessfulLoginWithEmptyFields() throws Exception {
        url = Resources.URL.LOGIN;
        when(request.getPathInfo()).thenReturn(url);
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        frontend.doPost(request, response);
        boolean emptyFields = stringWrite.toString().contains(Resources.InfoMessage.EMPTY_FIELDS);
        Assert.assertTrue(emptyFields);
    }
}
