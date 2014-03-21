package models;
import java.util.List;
import java.sql.SQLException;


public interface UsersDAO{
    public void addUser(UserEntity dataSet);
    public void updateUser(Long idUser, UserEntity dataSet) throws SQLException;
    public UserEntity getByUsername(String username) throws SQLException;
    public UserEntity getUserById(Long idUser) throws SQLException;
    public List<UserEntity> getAllUsers() throws SQLException;
    public void deleteUser(UserEntity dataSet) throws SQLException;
}