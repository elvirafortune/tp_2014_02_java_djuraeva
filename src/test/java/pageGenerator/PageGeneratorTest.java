package pageGenerator;

import frontend.Resources;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static frontend.PageGenerator.getPage;

/*
 * Created by elvira on 07.03.14.
 */
public class PageGeneratorTest {
    @Test
    public void testGetPage() throws Exception {
        String tmplSrc = Resources.Template.INDEX;
        Assert.assertTrue(getPage(tmplSrc, new HashMap<String, Object>()).contains("Index"));
        tmplSrc = Resources.Template.REGISTRATION;
        Assert.assertTrue(getPage(tmplSrc, new HashMap<String, Object>()).contains("Registration"));
        tmplSrc = Resources.Template.TIMER;
        Assert.assertTrue(getPage(tmplSrc, new HashMap<String, Object>()).contains("Timer"));
    }
}
