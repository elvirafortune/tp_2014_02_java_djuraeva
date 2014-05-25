package frontend;

import database.HibernateSettings.HibernateAbstract;
import database.HibernateSettings.HibernateUtilTest;
import message.Msg.MessageService;
import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import server.ServerConnection;
import utils.resources.Connection;
import utils.resources.Resource;
import utils.resources.Resources;

import java.util.NoSuchElementException;

/*
 * Created by elvira on 15.03.14.
 */
abstract class SeleniumPrepare {
    protected String testLogin;
    protected String testPassword;
    private Server server;

    private HibernateAbstract util = new HibernateUtilTest();
    private MessageService msg = new MessageService();
    protected AccountService accountService = new AccountService(util, msg);

    void initTest() throws Exception{
        testLogin = AccountServiceTest.getRandString(8) ;
        testPassword = AccountServiceTest.getRandString(8);
        server = ServerConnection.connect(((Connection)Resources.getInstance().getResource("data/connection.xml")).getTEST_PORT(), util);
        server.start();
    }

    void endTest() throws Exception{
        accountService.deleteUser(testLogin);
        server.stop();
    }

    void submit(WebDriver driver) throws NoSuchElementException{
        WebElement loginField = driver.findElement(By.name("login"));
        loginField.sendKeys(testLogin);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(testPassword);
        WebElement submitBtn = driver.findElement(By.name("button"));
        submitBtn.submit();
    }
}
