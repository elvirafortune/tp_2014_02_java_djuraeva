package message;

import frontend.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by elvira on 29.03.14.
 */
public class AddressService {
    private List<Address> accountServices = new ArrayList<>();

    public Address getAccountServiceAddress(){
        return accountServices.get(new Random().nextInt(accountServices.size()));
    }

    public void setAccountServiceAddress(Address address){
        accountServices.add(address);
    }
}
