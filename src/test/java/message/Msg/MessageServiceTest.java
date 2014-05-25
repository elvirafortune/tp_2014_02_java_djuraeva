package message.Msg;

import frontend.Frontendl;
import message.Abonent;
import message.Address;
import message.AddressService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by elvira on 03.04.14.
 */
public class MessageServiceTest {

    private class testMessage extends Msg{
        private boolean wasExecuted = false;

        public testMessage(Address from, Address to) {
            super(from, to);
        }

        public void exec(Abonent abonent) {
            wasExecuted = true;
        }
    }

    final private MessageService ms = new MessageService();
    final private Frontendl frontend = mock(Frontendl.class);
    final private Msg msg = mock(Msg.class);

    final private Frontendl someFrontendl = mock(Frontendl.class);


    @Before
    public void setUp(){
        Address frontendAddress = new Address();
        Address someAddress = new Address();
        when(frontend.getAddress()).thenReturn(frontendAddress); //AddService
        when(someFrontendl.getAddress()).thenReturn(someAddress);
        when(msg.getTo()).thenReturn(frontendAddress);  //SendMessage
    }

    @Test
    public void testAddService(){
        ms.addService(frontend);
        boolean serviceAdded =  ms.messages.get(frontend.getAddress()) != null;
        Assert.assertTrue(serviceAdded);
    }

    @Test
    public void testSendMessage(){
        ms.addService(frontend);
        ms.sendMessage(msg);
        boolean queueHasMessage = ms.messages.get(frontend.getAddress()).peek() != null;
        Assert.assertTrue(queueHasMessage);
    }

    @Test
    public void testExecForAbonentNullQueue(){
        ms.addService(frontend);
        ms.addService(someFrontendl);
        testMessage newMessage = new testMessage(frontend.getAddress(), someFrontendl.getAddress());
        ms.sendMessage(newMessage);
        ms.execForAbonent(frontend);
        Assert.assertFalse(newMessage.wasExecuted);
    }

    @Test
    public void testExecForAbonent(){
        ms.addService(frontend);
        ms.addService(someFrontendl);
        testMessage newMessage = new testMessage(frontend.getAddress(), someFrontendl.getAddress());
        ms.sendMessage(newMessage);
        ms.execForAbonent(someFrontendl);
        Assert.assertTrue(newMessage.wasExecuted);

    }
}
