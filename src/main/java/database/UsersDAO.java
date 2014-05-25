package database;

import java.sql.SQLException;
import java.util.List;

/*
 * Created by elvira on 01.03.14.
 */
public interface UsersDAO{
    public void save(UsersDataSet dataSet);
    public UsersDataSet readByName(String login);
    public UsersDataSet get(long id);
    public List<UsersDataSet> getAll();
    public void delete(UsersDataSet user);
}
