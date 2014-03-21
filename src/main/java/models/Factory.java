package models;

import Frontend.CreatedBy;


@CreatedBy(name = "elvira" , date = "11.03.14")
public class Factory {
    private static UsersDataSetDAO dao = new UsersDataSetDAO();

    public static boolean addUser(String username, String password){
        if (!isRegistered(username)){
            UserEntity user=new UserEntity(username, password);
            dao.addUser(user);
            return true;
        }
        return false;
    }

    public static boolean findUser(String username, String password){
        UserEntity user = dao.getByUsername(username);
        if (user == null)
        {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public static boolean isRegistered(String username){
        return dao.getByUsername(username) != null;
    }

    public static boolean deleteUser(String username){
        UserEntity user = dao.getByUsername(username);
        if (user != null){
            dao.deleteUser(user);
            return true;
        }
        return false;
    }
}
