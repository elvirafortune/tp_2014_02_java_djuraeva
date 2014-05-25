package message.Msg;

import message.Abonent;
import message.Address;
import message.AddressService;
import message.Msg.Msg;

import java.util.HashMap;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by elvira on 29.03.14.
 */
public class MessageService {
    public Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<Address, ConcurrentLinkedQueue<Msg>>();
    private AddressService addressService = new AddressService();

    public void addService(Abonent abonent){
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    public AddressService getAddressService(){
        return addressService;
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> messageQueue = messages.get(abonent.getAddress());
        if(messageQueue == null){
            return;
        }
        while(!messageQueue.isEmpty()){
            Msg message = messageQueue.poll(); //Retrieves and removes the head of this queue, or returns null if this queue is empty.
            message.exec(abonent);
        }
    }

    public void sendMessage(Msg message){
        Queue<Msg> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }
}
