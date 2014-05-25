package server;

import database.HibernateSettings.HibernateAbstract;
import frontend.AccountService;
import message.Msg.MessageService;

/**
 * Created by elvira on 05.04.14.
 */
public class AccountServiceCreator {
    static AccountService getNewAccountService(HibernateAbstract util, MessageService ms){
        AccountService accountService = null;
        try {
            accountService = new AccountService(util.getClass().newInstance(), ms);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return accountService;
    }
}
