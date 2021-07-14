package model.database;

import model.database.entity.UserContact;
import model.database.entity.UserWallet;
import model.database.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public  class DatabaseConnectionModel {

    private  SessionFactory buildSessionFactory () {

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

    public  Session getSessionObj()
    {
        return  buildSessionFactory().openSession();
    }

    public void closeConnection(Session session)
    {
        session.close();
    }


}
