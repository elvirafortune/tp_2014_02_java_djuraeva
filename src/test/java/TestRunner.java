import frontend.*;
import message.Msg.MessageService;
import message.Msg.MessageServiceTest;
import message.Msg.MsgGetUserID;
import message.Msg.MsgGetUserIDTest;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import pageGenerator.PageGeneratorTest;
import utils.ReflectionHelperTest;
import utils.resources.ResourcesTest;

/*
 * Created by elvira on 10.03.14.
 */
public class TestRunner {
    public static void main(String[] args) throws Exception{
        JUnitCore core = new JUnitCore();
        core.addListener(new TestListener());
        if (core.run(AccountServiceTest.class, FrontendTest.class, RoutingTest.class,
                PageGeneratorTest.class, AuthTest.class, /*RegTest.class,*/
                MessageServiceTest.class, MsgGetUserIDTest.class, ReflectionHelperTest.class,
                ResourcesTest.class).
                wasSuccessful())
        {
            System.out.println("ALL TESTS OK");
        }else
        {
            System.out.println("SOMETHING GOES WRONG!");
        }
        System.exit(0);
    }
}

class TestListener extends RunListener {

    @Override
    public void testStarted(Description desc) {
        //System.out.println("Started:" + desc.getDisplayName());
    }

    @Override
    public void testFinished(Description desc) {
        System.out.println("OK:" + desc.getDisplayName());
    }

    @Override
    public void testFailure(Failure fail) {
        System.out.println("Failed:" + fail.getDescription().getDisplayName() + " [" + fail.getMessage() + "]");
    }
}