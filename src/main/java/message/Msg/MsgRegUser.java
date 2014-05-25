package message.Msg;

import frontend.AccountService;
import message.Address;
import utils.resources.InfoMessages;
import utils.resources.Resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by elvira on 29.03.14.
 */
public class MsgRegUser extends MsgToAS {
    private String login;
    private String pass;
    private String sessionId;

    public MsgRegUser(Address from, Address to, String login, String pass, String sessionId) {
        super(from, to);
        this.login = login;
        this.pass = pass;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        MessageService accountServiceMS = accountService.getMessageSystem();
        try{
            if (!accountService.addUser(login, pass)){
                accountServiceMS.sendMessage(new MsgRegStatus(getTo(), getFrom(), sessionId,
                        ((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getDUPLICATE_LOGIN()));
            }
            else{
                accountServiceMS.sendMessage(new MsgRegStatus(getTo(), getFrom(), sessionId, "OK"));
            }
        }catch (org.hibernate.service.UnknownServiceException e){
            accountServiceMS.sendMessage(new MsgRegStatus(getTo(), getFrom(), sessionId, ((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getDB_UNREACHABLE()));
        }

    }
}
