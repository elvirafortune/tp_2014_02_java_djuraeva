package database.HibernateSettings;

import database.HibernateSettings.HibernateAbstract;
import database.UsersDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import utils.resources.Connection;
import utils.resources.Resources;
import utils.resources.URL;

/*
 * Created by elvira on 01.03.14.
 */
public class HibernateUtil extends HibernateAbstract {

    @Override
    protected SessionFactory createSessionFactory(){
        Connection conResource = (Connection) Resources.getInstance().getResource("data/connection.xml");
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", conResource.getDB());
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "390191");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }
}
