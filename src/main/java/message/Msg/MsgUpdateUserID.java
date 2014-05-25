package message.Msg;

import frontend.Frontendl;
import message.Address;

/**
 * Created by elvira on 29.03.14.
 */
public class MsgUpdateUserID extends MsgToFrontend {

    private String sessionId;
    private String status;

    public MsgUpdateUserID(Address from, Address to, String sessionId, String status) {
        super(from, to);
        this.sessionId = sessionId;
        this.status = status;
    }

    @Override
    void exec(Frontendl frontend) {
        frontend.setUserSessionStatus(sessionId, status);
    }

}
