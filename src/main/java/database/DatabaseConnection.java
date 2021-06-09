package database;

import database.entity.UserContact;
import database.entity.UserWallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import database.entity.User;

public class DatabaseConnection {


    private static SessionFactory buildSessionFactory () {

        SessionFactory sessionFactoryObj;

        Configuration configObj = new Configuration();
        configObj.addAnnotatedClass(User.class);
        configObj.addAnnotatedClass(UserContact.class);
        configObj.addAnnotatedClass(UserWallet.class);
        configObj.configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public static Session getSessionObj()
    {
        Session sessionObj;
        return sessionObj = buildSessionFactory().openSession();
    }


}
