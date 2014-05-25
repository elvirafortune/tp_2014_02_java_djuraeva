package frontend;

import message.Address;
import message.AddressService;

/**
 * Created by elvira on 29.03.14.
 */
public class UserSession {
    private Address accountService;

    private String name;
    private String sessionId;
    private String userStatus;

    public UserSession(String sessionId, String name, AddressService addressService) {
        this.sessionId = sessionId;
        this.name = name;
        this.accountService = addressService.getAccountServiceAddress();    //Аккаунт сервис зарегал себя в адрес-сервисе при создании
        this.userStatus = "";
    }

    public Address getAccountServiceAddress() {
        return accountService;
    }

    public String getName(){
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String _userStatus) {
        userStatus = _userStatus;
    }
}
