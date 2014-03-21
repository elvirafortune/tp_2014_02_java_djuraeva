package models;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import java.sql.SQLException;
import java.util.List;


public class UsersDataSetDAO implements UsersDAO {

      public void addUser(UserEntity user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void updateUser(Long idUser, UserEntity user) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
    }

    public void deleteUser(UserEntity user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
    }

    public UserEntity getByUsername(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("username", username));
        UserEntity user = (UserEntity)criteria.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    public UserEntity getUserById(Long id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity  user = (UserEntity) session.load(UserEntity.class, id);
        transaction.commit();
        session.close();
        return user;
    }


    public List<UserEntity>  getAllUsers() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(UserEntity.class);
        List<UserEntity> users = (List<UserEntity>)criteria.list();
        transaction.commit();
        session.close();
        return users;
    }

}