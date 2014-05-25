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
 * Created by elvira on 10.03.14.
 */
public class RegTest extends SeleniumPrepare {

    public Boolean testRegistration(@NotNull String url) {

        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
        try {
            submit(driver);
        }catch (NoSuchElementException e){
            return false;
        }
        Boolean result;
        try{
            result = new WebDriverWait(driver, 1).until(
                    new ExpectedCondition<Boolean>() {
                        @Override
                        @NotNull
                        public Boolean apply(@NotNull WebDriver d) {
                            try{
                                WebElement el = d.findElement(By.id("status"));
                                return !el.getText().equals("");
                            }catch (NoSuchElementException e){
                                return false;
                            }
                        }
                    }
            );
        }catch (WebDriverException e){
            result = false;
        }
        driver.quit();
        return result && accountService.isRegistered(testLogin);
    }

    @Before
    public void setUp() throws Exception{
        initTest();
    }

    //@Test
    public void testReg(){
        boolean tryReg = testRegistration("http://localhost:" + Resources.URL.TEST_PORT + Resources.URL.REGISTRATION_FORM);
        assertTrue(tryReg);
    }

    @After
    public void tearDown() throws Exception{
        endTest();
    }
}
