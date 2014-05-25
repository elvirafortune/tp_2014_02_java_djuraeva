package frontend;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import frontend.UserSession;
import message.Abonent;
import message.Address;
import message.Msg.*;
import utils.resources.InfoMessages;
import utils.resources.Resources;
import utils.resources.Template;
import utils.resources.URL;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Created by elvira on 15.02.14.
 */

public class Frontendl extends HttpServlet implements Abonent, Runnable{
    private static final DateFormat FORMATTER = new SimpleDateFormat("HH.mm.ss");
    private final MessageService messageService;
    private final Address address;

    private Map<String, UserSession> sessionIdToUserSession = new HashMap<>();
    private Map<String, String> sessionIdToRegistered = new HashMap<>();

    public Frontendl(MessageService _messageService){
        messageService = _messageService;
        address = new Address();
        messageService.addService(this);
    }

    public Address getAddress(){
        return address;
    }

    public static String getTime() {
        return FORMATTER.format(new Date());
    }

    public void setUserSessionStatus(String sessionId, String status) {
        UserSession userSession = sessionIdToUserSession.get(sessionId);
        if (userSession == null) {
            return;
        }
        userSession.setUserStatus(status);
    }

    public void setUserRegistrationStatus(String sessionId, String _status){
        sessionIdToRegistered.put(sessionId, _status);
    }

    public void setSessionAuthStatus(String sessionId, UserSession userSession){
        sessionIdToUserSession.put(sessionId, userSession);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=utf-8");

        final String path = request.getPathInfo();
        String sessionID = request.getSession().getId();

        URL urlResource = (URL) Resources.getInstance().getResource("data/url.xml");
        Template templateResource = (Template) Resources.getInstance().getResource("data/template.xml");

        if (path.equals(urlResource.getTIMER_PAGE())) {
            UserSession userSession = sessionIdToUserSession.get(sessionID);
            if (userSession == null){
                response.sendRedirect(urlResource.getAUTHFORM());
                return;
            }
            Map <String, Object> responseData = new HashMap<>();
            responseData.put("refreshPeriod", "1000");
            responseData.put("serverTime", getTime());
            responseData.put("userID", userSession.getUserStatus());
            sendPage(response, templateResource.getTIMER(), responseData);
            return;
        }
        if (path.equals(urlResource.getREGISTRATION_FORM())) {
            sendPage(response, templateResource.getREGISTRATION(), "");
            return;
        }
        if (path.equals(urlResource.getAUTHFORM())) {
            sendPage(response, templateResource.getINDEX(), "");
            return;
        }
        if (path.equals("/getRegistered")) {
            String current_status;
            current_status = sessionIdToRegistered.get(sessionID);
            sessionIdToRegistered.remove(sessionID);
            if (current_status != null)
                response.getWriter().println(current_status);
            return;
        }
        response.sendRedirect("/");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        final String path = request.getPathInfo();
        String login = request.getParameter("login");// .getParameter("login");
        String pass = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");

        response.setStatus(HttpServletResponse.SC_OK);

        URL urlResource = (URL) Resources.getInstance().getResource("data/url.xml");
        Template templateResource = (Template) Resources.getInstance().getResource("data/template.xml");
        InfoMessages infoMessagesResource = (InfoMessages) Resources.getInstance().getResource("data/info_messages.xml");

        if (login.isEmpty() || pass.isEmpty()){
            String template;
            if (path.equals(urlResource.getLOGIN())){
                template = templateResource.getINDEX();
            }
            else{
                template = templateResource.getREGISTRATION();
            }
            sendPage(response, template, infoMessagesResource.getEMPTY_FIELDS());
            return;
        }

        if (path.equals(urlResource.getLOGIN())) {
            login(login, pass, request, response);
            return;
        }
        if (path.equals(urlResource.getREGISTER())){
            register(login, pass, request.getSession().getId(), response);
        }
    }

    public void sendPage(@NotNull HttpServletResponse response, @NotNull String tmpl,
                          @Nullable String errorMsg) throws IOException{
        Map <String, Object> responseData = new HashMap<>();
        responseData.put("error", errorMsg);
        response.getWriter().println(PageGenerator.getPage(tmpl, responseData));
    }

    public void sendPage(@NotNull HttpServletResponse response, @NotNull String tmpl,
                          @NotNull Map<String, Object>responseData) throws IOException{
        response.getWriter().println(PageGenerator.getPage(tmpl, responseData));
    }

    private void login(String login, String pass, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        if(!session.isNew())
        {
            session.invalidate();
            session = request.getSession();
        }
        String sessionID = session.getId();
        UserSession userSession = new UserSession(sessionID, login, messageService.getAddressService());
        sessionIdToUserSession.put(sessionID, userSession);
        userSession.setUserStatus("wait for authorization");

        Address FrontendlAddress = getAddress();
        Address accountServiceAddress = userSession.getAccountServiceAddress();

        Msg message = createMsgGetUserID(FrontendlAddress, accountServiceAddress, login, pass, sessionID);
        messageService.sendMessage(message);

        response.sendRedirect("/timer");
    }

    private void register(String login, String pass, String sessionId, HttpServletResponse response)
            throws ServletException, IOException
    {
        Address FrontendlAddress = getAddress();
        Address accountServiceAddress = messageService.getAddressService().getAccountServiceAddress();
        MsgRegUser msg = createMsgRegUser(FrontendlAddress, accountServiceAddress, login, pass, sessionId);
        messageService.sendMessage(msg);
        Template templateResource = (Template) Resources.getInstance().getResource("data/template.xml");
        sendPage(response, templateResource.getREGISTRATION(), "wait");
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            messageService.execForAbonent(this);
            try{
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public MsgGetUserID createMsgGetUserID(Address from, Address to, String login, String pass, String sessionId){
        return new MsgGetUserID(from, to, login, pass, sessionId);
    }

    public MsgRegUser createMsgRegUser(Address from, Address to, String login, String pass, String sessionId){
        return new MsgRegUser(from, to, login, pass, sessionId);
    }

}
