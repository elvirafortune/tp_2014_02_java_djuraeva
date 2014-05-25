package database;

import database.HibernateSettings.HibernateAbstract;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/*
 * Created by elvira on 01.03.14.
 */
public class UsersDataSetDAO implements UsersDAO{

    private HibernateAbstract util;

    public UsersDataSetDAO(HibernateAbstract _util){
        util = _util;
    }

    public void save(UsersDataSet dataSet){
        Session session = util.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.save(dataSet);
        trx.commit();
        session.close();
    }

    public void delete(UsersDataSet user){
        Session session = util.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.delete(user);
        trx.commit();
        session.close();
    }

    public UsersDataSet readByName(String login){
        Session session = util.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        Criteria crit = session.createCriteria(UsersDataSet.class);
        crit.add(Restrictions.eq("login", login));
        UsersDataSet user = (UsersDataSet)crit.uniqueResult();
        trx.commit();
        session.close();
        return user;
    }

    public UsersDataSet get(long id){
        Session session = util.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        UsersDataSet user = (UsersDataSet) session.load(UsersDataSet.class, id);
        trx.commit();
        session.close();
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<UsersDataSet> getAll(){
        Session session = util.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        Criteria crit = session.createCriteria(UsersDataSet.class);
        List<UsersDataSet> users = (List<UsersDataSet>) crit.list();
        trx.commit();
        session.close();
        return users;
    }

}
