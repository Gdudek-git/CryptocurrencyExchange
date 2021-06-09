package database;

import database.entity.User;
import database.entity.UserContact;
import database.entity.UserWallet;
import org.hibernate.Session;

import java.sql.SQLException;

public class RegisterUser {

    DatabaseConnection databaseConnection;
    Session sessionObj;


    public void establishConnection()
    {
        databaseConnection = new DatabaseConnection();
        sessionObj =  databaseConnection.getSessionObj();
    }

    public void closeConnection()
    {
        sessionObj.close();
    }

    public void register(String firstName,String lastName, String username, String phoneNumber, String country, String email, String gender, String password)
    {

        User userObj = new User();
        UserWallet userWalletObj = new UserWallet();
        UserContact userContactObj = new UserContact();

        try {
            addUserEntity(sessionObj, userObj, firstName, lastName, username, password, gender);
            addUserWalletEntity(sessionObj, userObj, userWalletObj);
            addUserContactEntity(sessionObj, userObj, userContactObj, phoneNumber, email, country);
        }
        catch (Exception sqlException)
        {
            if (null != sessionObj.getTransaction()) {
                sessionObj.getTransaction().rollback();
            }
        }

    }

    private void addUserEntity(Session sessionObj, User userObj, String firstName,String lastName, String username,String password,String gender)
    {
            sessionObj.beginTransaction();
            userObj.setUsername(username);
            userObj.setFirstName(firstName);
            userObj.setLastName(lastName);
            userObj.setGender(gender);
            userObj.setPassword(password);
            sessionObj.save(userObj);
            sessionObj.getTransaction().commit();
            sessionObj.clear();
    }

    private void addUserWalletEntity(Session sessionObj, User userObj, UserWallet userWalletObj)
    {
        sessionObj.beginTransaction();
        userWalletObj.setUser(userObj);
        userWalletObj.setEur(500);
        userWalletObj.setPln(1000);
        userWalletObj.setUsd(500);
        sessionObj.save(userWalletObj);
        sessionObj.getTransaction().commit();
        sessionObj.clear();
    }

    private void addUserContactEntity(Session sessionObj, User userObj, UserContact userContactObj, String phoneNumber,String email, String country)
    {
        sessionObj.beginTransaction();
        userContactObj.setUser(userObj);
        userContactObj.setPhoneNumber(phoneNumber);
        userContactObj.setCountry(country);
        userContactObj.setEmail(email);
        sessionObj.save(userContactObj);
        sessionObj.getTransaction().commit();
        sessionObj.clear();
    }


    public String checkIfUsernameInDatabase(String username)
    {
        sessionObj.beginTransaction();
        User user = sessionObj.get(User.class, username);
        sessionObj.getTransaction().commit();
        sessionObj.clear();
        if(user==null)
        {
            return"valid";
        }
        else
        {
            return"That username is taken";
        }
    }
}
