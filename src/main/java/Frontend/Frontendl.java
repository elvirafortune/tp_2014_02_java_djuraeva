package Frontend;
/**
 * Created by Elvira on 21.02.14.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import models.Factory;

import java.util.concurrent.atomic.AtomicLong;

public class Frontendl extends HttpServlet {

    private static final AtomicLong idUserGenerator = new AtomicLong();
    private Factory factory;

    public Frontendl(){
        this.factory = new Factory();
    }

    public static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    } //сократить метод

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        HttpSession session= request.getSession();
        String result;
        try
        {
            result = session.getAttribute("result").toString();
            session.removeAttribute("result");
        }
        catch (NullPointerException e)
        {
            result  = "";
        }
        switch(request.getPathInfo()) {
            case Remarks.Url.AUTHORIZATION:
                if(result.equals("not"))
                    pageVariables.put("result", Remarks.Warnings.FAIL );
                else {
                    pageVariables.put("result","");
                }
                response.getWriter().println(PageGenerator.getPage(Remarks.Page.AUTHORIZATION, pageVariables));
                break;

            case Remarks.Url.REGISTRATION:
                switch(result) {
                    case "error":
                        pageVariables.put("result", Remarks.Warnings.FIELDS );
                        break;
                    case "User":
                        pageVariables.put("result", Remarks.Warnings.USER );
                        break;
                    case "added":
                        pageVariables.put("result", Remarks.Warnings.REGISTRATION );
                        break;
                    default:
                        pageVariables.put("result","");
                        break;
                }
                response.getWriter().println(PageGenerator.getPage(Remarks.Page.REGISTRATION, pageVariables));
                break;

            case Remarks.Url.SESSION:
                Long idUser;
                try
                {
                    idUser = (Long)session.getAttribute("idUser");
                }
                catch (NullPointerException e)
                {
                    idUser = null;
                }
                if(idUser == null)
                {
                    response.sendRedirect(Remarks.Url.INDEX);
                }
                pageVariables.put("refreshPeriod", Remarks.REFRESH_TIME);
                pageVariables.put("serverTime", getTime());
                pageVariables.put("idUser", idUser);
                response.getWriter().println(PageGenerator.getPage(Remarks.Page.SESSION, pageVariables));
                break;

            case Remarks.Url.INDEX:
                response.getWriter().println(PageGenerator.getPage(Remarks.Page.INDEX , pageVariables));
                break;

            default:
                response.sendRedirect(Remarks.Url.INDEX);
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession session = request.getSession();
        switch (request.getPathInfo()) {
            case Remarks.Url.AUTHORIZATION:
                if(factory.findUser(username, password))
                {
                    try
                    {
                        if( session.getAttribute("idUser") != null )
                        {
                            session.removeAttribute("idUser");
                        }
                        Long idUser = idUserGenerator.getAndIncrement();
                        session.setAttribute("idUser", idUser);
                        session.removeAttribute("result");
                        response.sendRedirect(Remarks.Url.SESSION);
                    }
                    catch (NullPointerException e)
                    {
                        response.sendRedirect(Remarks.Url.INDEX);
                    }
                }
                else
                {
                    session.setAttribute("result" , "not");
                    response.sendRedirect(Remarks.Url.AUTHORIZATION);
                }
                break;

            case Remarks.Url.REGISTRATION :
                if(username.equals("") || password.equals("")) {
                    session.setAttribute("result", "error");
                    response.sendRedirect(Remarks.Url.REGISTRATION );
                }
                else {
                    if(!Factory.addUser(username , password)) {
                        session.setAttribute("result", "User");
                        response.sendRedirect(Remarks.Url.REGISTRATION);
                    }
                    else {
                        session.setAttribute("result" , "added");
                        response.sendRedirect(Remarks.Url.REGISTRATION);
                    }
                }
                break;

        }
    }
}

//два сервлета один с регистрацией, другой с регистрации
// по имени найти датасет, найти юзера и проверить совпаадение пароля