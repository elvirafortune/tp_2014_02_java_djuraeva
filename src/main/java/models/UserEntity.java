package models;
/**
 * Created by Elvira on 11.03.14.
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user", schema = "", catalog = "mydb")

public class UserEntity implements Serializable {

    private int idUser;
    private String password;
    private String username;

    @Id
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (idUser != that.idUser) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public UserEntity() {
        this.setIdUser(-1);
        this.setUsername("\0");
        this.setPassword("\0");
    }

    public UserEntity(String login, String password) {
        this.setIdUser(-1);
        this.setUsername(login);
        this.setPassword(password);
    }


}
