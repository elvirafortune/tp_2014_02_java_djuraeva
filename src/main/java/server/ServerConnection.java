package server;

import database.HibernateSettings.HibernateAbstract;
import database.HibernateSettings.HibernateUtil;
import frontend.AccountService;
import frontend.Frontendl;
import message.Msg.MessageService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/*
 * Created by elvira on 10.03.14.
 */
public class ServerConnection {
    public static Server connect(Integer port, HibernateAbstract util) {
        MessageService ms = new MessageService();
        AccountService accountService = AccountServiceCreator.getNewAccountService(util, ms);
        AccountService accountService1 = AccountServiceCreator.getNewAccountService(util, ms);
        Frontendl frontend = new Frontendl(ms);
        new Thread(frontend).start();
        new Thread(accountService).start();
        new Thread(accountService1).start();
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);
        return server;
    }
}
