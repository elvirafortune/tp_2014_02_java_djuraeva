package message.Msg;

import frontend.AccountService;
import message.Abonent;
import message.Address;

/**
 * Created by elvira on 29.03.14.
 */
public abstract class MsgToAS extends Msg{
    public MsgToAS(Address from, Address to) {
        super(from, to);
    }

    @Override
    void exec(Abonent abonent) {
        if(abonent instanceof AccountService){
            exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService accountService);
}
