package database;

import javax.persistence.*;
import java.io.Serializable;

/*
 * Created by elvira on 01.03.14.
 */

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public UsersDataSet(){}

    public UsersDataSet(String _login, String _password){
        setId(-1L);
        setLogin(_login);
        setPassword(_password);
    }

    public String getLogin() {
        return login;
    }
    public String getPassword(){
        return password;
    }
    public Long getId() {return id;
    }

    public void setId(Long _id){
        id = _id;
    }
    public void setLogin(String _login){
        login = _login;
    }
    public void setPassword(String _password){
        password = _password;
    }


}
