package message.Msg;

import frontend.AccountService;
import message.Address;
import org.apache.bcel.classfile.Unknown;
import utils.resources.InfoMessages;
import utils.resources.Resources;

import java.net.UnknownServiceException;

/**
 * Created by elvira on 29.03.14.
 */
public class MsgGetUserID extends MsgToAS{
    private String login;
    private String sessionId;
    private String pass;

    public MsgGetUserID(Address from, Address to, String login, String pass, String sessionId) {
        super(from, to);
        this.login = login;
        this.pass = pass;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        MessageService accountServiceMS = accountService.getMessageSystem();
        try{
            String status = accountService.getUserStatus(login, pass);
            MsgUpdateUserID msg = createMsgUpdateUserID(getTo(), getFrom(), sessionId, status);
            accountServiceMS.sendMessage(msg);
        }catch (IllegalArgumentException e){
            accountServiceMS.sendMessage(createMsgUpdateUserID(getTo(), getFrom(), sessionId,
                    ((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getNOT_REGISTERED()));
        }catch (org.hibernate.service.UnknownServiceException e){
            accountServiceMS.sendMessage(createMsgUpdateUserID(getTo(), getFrom(), sessionId,
                    ((InfoMessages) Resources.getInstance().getResource("data/info_messages.xml")).getDB_UNREACHABLE()));
        }catch (Throwable e ){
            e.printStackTrace();
        }


        /*
        try{
            if (accountService.checkUser(login, pass)){
                Long id = accountService.getUserId(login);
                MsgUpdateUserID msg = createMsgUpdateUserID(getTo(), getFrom(), sessionId, id.toString());
                accountServiceMS.sendMessage(msg);
            }else{
                accountServiceMS.sendMessage(createMsgUpdateUserID(getTo(), getFrom(), sessionId, Resources.InfoMessage.WRONG_PASSWORD));
            }
        }catch (IllegalArgumentException e){
            accountServiceMS.sendMessage(createMsgUpdateUserID(getTo(), getFrom(), sessionId, Resources.InfoMessage.NOT_REGISTERED));
        }catch (org.hibernate.service.UnknownServiceException e){
            accountServiceMS.sendMessage(createMsgUpdateUserID(getTo(), getFrom(), sessionId, Resources.InfoMessage.DB_UNREACHABLE));
        }catch (Throwable e ){
            e.printStackTrace();
        }*/
    }

    public MsgUpdateUserID createMsgUpdateUserID(Address from, Address to, String sessionId, String status){
        return new MsgUpdateUserID(from, to, sessionId, status);
    }
}
