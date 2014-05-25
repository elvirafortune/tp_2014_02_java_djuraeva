package utils;

import org.junit.Assert;
import org.junit.Test;
import utils.resources.Resources;
import utils.resources.URL;

/**
 * Created by Sergey on 19.04.14.
 */
public class ReflectionHelperTest{
    @Test
    public void testCreateInstanceGood() throws Exception {
        Object obj = ReflectionHelper.createInstance("utils.resources.URL");
        boolean created = obj.getClass().equals(URL.class);
        Assert.assertTrue(created);
    }

    @Test
    public void testSetFieldValue() throws Exception {
        URL url = new URL();
        ReflectionHelper.setFieldValue(url, "LOGIN", "log");
        boolean set = url.getLOGIN().equals("log");
        Assert.assertTrue(set);
    }
}
