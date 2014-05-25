package message.Msg;

import frontend.AccountService;
import frontend.Frontendl;
import message.Address;
import org.eclipse.jetty.server.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Created by elvira on 29.03.14.
 */
public class MsgRegStatus extends MsgToFrontend{
    private String status;
    private String sessionId;

    public MsgRegStatus(Address from, Address to, String _sessionId, String _status) {
        super(from, to);
        status = _status;
        sessionId = _sessionId;
    }

    @Override
    void exec(Frontendl frontend) {
        frontend.setUserRegistrationStatus(sessionId, status);
    }
}
