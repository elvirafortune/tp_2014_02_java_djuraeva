package message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by elvira on 29.03.14.
 */
public class Address {
    static private AtomicInteger abonentIDCreator = new AtomicInteger();
    private int abonentID;

    public Address(){
        this.abonentID = abonentIDCreator.incrementAndGet();
    }

    public int getAddress(){
        return this.abonentID;
    }
}
