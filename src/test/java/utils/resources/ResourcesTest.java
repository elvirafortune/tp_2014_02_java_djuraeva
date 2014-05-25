package utils.resources;

import org.junit.Assert;
import org.junit.Test;
import utils.SaxReader;
import utils.VFS.VFS;

import java.io.File;

import static org.mockito.Mockito.mock;

/**
 * Created by Sergey on 19.04.14.
 */
public class ResourcesTest {
    private String testFile = "data/test_file.xml";

    @Test
    public void testGetResource() throws Exception {
        Resource a = Resources.getInstance().getResource(testFile);
        Assert.assertTrue(a != null);
    }

/*    @Test
    public void testXMLReader() throws Exception {
        Object obj = SaxReader.readXML(testFile);
        boolean type = obj.getClass() == TestObject.class;
        boolean same = ((TestObject)obj).a == new TestObject().a && ((TestObject) obj).b.equals(new TestObject().b);
        Assert.assertTrue(type);
        Assert.assertTrue(same);
    }*/
}
