package frontend;

import database.HibernateSettings.HibernateAbstract;
import database.HibernateSettings.HibernateUtilTest;
import message.Msg.MessageService;
import org.junit.*;

/*
 * Created by elvira on 06.03.14.
 */
public class AccountServiceTest {
    private String testLogin;
    private String testPassword;
    private final HibernateAbstract util = new HibernateUtilTest();
    private final MessageService msg = new MessageService();
    private final AccountService accountService = new AccountService(util, msg);
    private boolean userAdded;

    public static String getRandString(int length){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append((char) (65 + Math.random() * 25));
        return result.toString();
    }

    @Before
    public void setUp() throws Exception{
        testLogin = getRandString(8);
        testPassword = "pass";
        userAdded = accountService.addUser(testLogin, testPassword);
    }

    @After
    public void tearDown() throws Exception{
        accountService.deleteUser(testLogin);
    }

    @Test
    public void testAddUserGood() throws Exception {
        Assert.assertTrue(userAdded);
    }

    @Test
    public void testAddUserBad() throws Exception {
        boolean anotherAddUser = accountService.addUser(testLogin, testPassword);
        Assert.assertFalse(anotherAddUser);
    }

    @Test
    public void testCheckUserGood() throws Exception {
        boolean checkUserGood = accountService.checkUser(testLogin, testPassword);
        Assert.assertTrue(checkUserGood);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckUserNotRegistered() throws Exception {
        accountService.checkUser(getRandString(8), testPassword);
    }

    @Test
     public void testCheckUserBad() throws Exception {
        testPassword = getRandString(8);
        boolean checkUser = accountService.checkUser(testLogin, testPassword);
        Assert.assertFalse(checkUser);
     }

    @Test
    public void testIsRegisteredGood() throws Exception {
        boolean isRegistered = accountService.isRegistered(testLogin);
        Assert.assertTrue(isRegistered);
    }

    @Test
    public void testIsRegisteredBad() throws Exception {
        boolean isRegistered = accountService.isRegistered(getRandString(8));
        Assert.assertFalse(isRegistered);
    }

    @Test
    public void testDeleteUserGood() throws Exception {
        boolean delete = accountService.deleteUser(testLogin);
        Assert.assertTrue(delete);
    }

    @Test
    public void testDeleteUserBad() throws Exception {
        accountService.deleteUser(testLogin);
        boolean anotherDelete = accountService.deleteUser(testLogin);
        Assert.assertFalse(anotherDelete);
    }
}
