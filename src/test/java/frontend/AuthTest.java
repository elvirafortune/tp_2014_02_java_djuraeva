package frontend;

import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/*
 * Created by elvira on 08.03.14.
 */
public class AuthTest extends SeleniumPrepare {

    public Boolean testLogin(@NotNull String url) {

        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
        try {
            submit(driver);
        }catch (NoSuchElementException e){
            return false;
        }
        Boolean result;
        try{
            result = new WebDriverWait(driver, 10).until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        @NotNull
                        public Boolean apply(@NotNull WebDriver d) {
                            WebElement el = d.findElement(By.id("userID"));
                            return !el.getText().equals("");
                        }
                    }
            );
        }catch (WebDriverException e){
            result = false;
        }
        driver.quit();
        return result;
    }

    @Before
    public void setUp() throws Exception{
        initTest();
    }

    @Before
    public void addUser() {
        accountService.addUser(testLogin, testPassword);
    }

    @Test
    public void testAuth(){
        boolean tryLogin = testLogin("http://localhost:" + Resources.URL.TEST_PORT + Resources.URL.AUTHFORM);
        assertTrue(tryLogin);
    }

    @After
    public void tearDown() throws Exception{
        endTest();
    }
}