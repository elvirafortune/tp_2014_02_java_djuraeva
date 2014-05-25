package frontend;

import database.HibernateSettings.HibernateAbstract;
import database.UsersDataSet;
import database.UsersDataSetDAO;
import message.Abonent;
import message.Address;
import message.Msg.MessageService;
import utils.resources.InfoMessages;
import utils.resources.Resources;

/*
 * Created by elvira on 22.02.14.
 */
public class AccountService implements Runnable, Abonent{
    private UsersDataSetDAO dao;
    private Address address;
    private MessageService messageService;

    public AccountService(HibernateAbstract util, MessageService _messageService){
        dao = new UsersDataSetDAO(util);
        address = new Address();
        messageService = _messageService;
        messageService.addService(this);
        messageService.getAddressService().setAccountServiceAddress(address);
    }

    public Address getAddress(){
        return address;
    }

    public boolean addUser(String login, String password){
        if (!isRegistered(login)){
            dao.save(new UsersDataSet(login, password));
            return true;
        }
        return false;
    }

    public boolean checkUser(String userName, String password){
        final UsersDataSet user = dao.readByName(userName);
        if (user == null){
            throw new IllegalArgumentException(((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getNOT_REGISTERED());
        }

        return user.getPassword().equals(password);
    }

    public boolean isRegistered(String userName){
        return dao.readByName(userName) != null;
    }

    public boolean deleteUser(String login){
        UsersDataSet user = dao.readByName(login);
        if (user != null){
            dao.delete(user);
            return true;
        }
        return false;
    }

    public Long getUserId(String name) {
        UsersDataSet user = dao.readByName(name);
        return user.getId();
    }

    public String getUserStatus(String userName, String password){
        final UsersDataSet user = dao.readByName(userName);
        if (user == null)
            throw new IllegalArgumentException(((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getNOT_REGISTERED());
        if (user.getPassword().equals(password))
            return user.getId().toString();
        return ((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getWRONG_PASSWORD();
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            messageService.execForAbonent(this);
            try{
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public MessageService getMessageSystem(){
        return messageService;
    }
}
