package database.HibernateSettings;

import org.hibernate.SessionFactory;

/*
 * Created by elvira on 15.03.14.
 */
public abstract class HibernateAbstract {
    private SessionFactory SESSION_FACTORY = createSessionFactory();
    public SessionFactory getSessionFactory(){return SESSION_FACTORY;}
    abstract SessionFactory createSessionFactory();
}
