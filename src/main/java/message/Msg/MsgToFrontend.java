package message.Msg;

import frontend.Frontendl;
import message.Abonent;
import message.Address;

/**
 * Created by elvira on 29.03.14.
 */
public abstract class MsgToFrontend extends Msg{
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof Frontendl){
            exec((Frontendl)abonent);
        }
    }

    abstract void exec(Frontendl frontend);
}
