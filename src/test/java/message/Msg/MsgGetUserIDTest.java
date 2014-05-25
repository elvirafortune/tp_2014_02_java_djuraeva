package message.Msg;

import frontend.AccountService;
import frontend.AccountServiceTest;
import frontend.Resources;
import message.Address;
import org.hibernate.service.UnknownServiceException;
import org.junit.Before;
import org.junit.Test;
import org.hibernate.*;

import static org.mockito.Mockito.*;

/*
 * Created by elvira on 03.04.14.
 */
public class MsgGetUserIDTest {
    final private Address address = new Address();
    final private AccountService accountService = mock(AccountService.class);
    final private MessageService ms = mock(MessageService.class);
    final private MsgUpdateUserID msgUpdateUserID = mock(MsgUpdateUserID.class);

    final private String login = AccountServiceTest.getRandString(5);
    final private String sessionId = AccountServiceTest.getRandString(5);
    final private String pass = AccountServiceTest.getRandString(5);
    private String status;

    private MsgGetUserID toTest = spy(new MsgGetUserID(address, address, login, pass, sessionId));

    @Before
    public void setUp(){
        when(accountService.getMessageSystem()).thenReturn(ms);
        when(accountService.getUserId(login)).thenReturn(5L);
    }

    @Test
    public void testExecOk(){
        Long userID = 5L;
        when(accountService.getUserStatus(login, pass)).thenReturn(userID.toString());
        status = userID.toString();
        exec();
        verify(ms, atLeastOnce()).sendMessage(msgUpdateUserID);

    }

    @Test
    public void testExecWrongPass(){
        when(accountService.getUserStatus(login, pass)).thenReturn(Resources.InfoMessage.WRONG_PASSWORD);
        status = Resources.InfoMessage.WRONG_PASSWORD;
        exec();
        verify(ms, atLeastOnce()).sendMessage(msgUpdateUserID);
    }

    @Test
    public void testExecNoUser(){
        when(accountService.getUserStatus(login, pass)).thenThrow(new IllegalArgumentException());
        status = Resources.InfoMessage.NOT_REGISTERED;
        exec();
        verify(ms, atLeastOnce()).sendMessage(msgUpdateUserID);
    }

    @Test
    public void testExecDBError(){
        when(accountService.getUserStatus(login, pass)).thenThrow(new UnknownServiceException(AccountService.class));
        status = Resources.InfoMessage.DB_UNREACHABLE;
        exec();
        verify(ms, atLeastOnce()).sendMessage(msgUpdateUserID);
    }

    public void exec(){
        doReturn(msgUpdateUserID).when(toTest).createMsgUpdateUserID(address, address, sessionId, status);
        toTest.exec(accountService);
    }
}
