package database;

import database.entity.User;
import database.entity.UserContact;
import database.entity.UserWallet;
import org.hibernate.Session;
import validation.Valid;

public class RegisterUser {

    Session sessionObj;

    public Session getSessionObj()
    {
        return  sessionObj;
    }

    public void establishConnection()
    {
        sessionObj =  DatabaseConnection.getInstance().getSessionObj();
    }

    public void closeConnection()
    {
        if(sessionObj!=null) {
            sessionObj.close();
        }
    }

    public void register(String firstName,String lastName, String username, String phoneNumber, String country, String email, String gender, String password)
    {

        User userObj = new User();
        setUserEntityData(userObj,firstName,lastName,username,phoneNumber,country,email,gender,password);
        try {
            addUserEntityToDatabase(sessionObj, userObj);
        }
        catch (Exception sqlException)
        {
            if (null != sessionObj.getTransaction()) {
                sessionObj.getTransaction().rollback();
            }
        }
    }

    private void setUserEntityData(User userObj,String firstName,String lastName, String username, String phoneNumber, String country, String email, String gender,String password)
    {
        userObj.setUsername(username);
        userObj.setFirstName(firstName);
        userObj.setLastName(lastName);
        userObj.setGender(gender);
        userObj.setPassword(password);

        userObj.getUserContact().setUser(userObj);
        userObj.getUserContact().setPhoneNumber(phoneNumber);
        userObj.getUserContact().setEmail(email);
        userObj.getUserContact().setCountry(country);

        userObj.getUserWallet().setUser(userObj);
        userObj.getUserWallet().setEur(500);
        userObj.getUserWallet().setPln(1000);
        userObj.getUserWallet().setUsd(500);

    }

    private void addUserEntityToDatabase(Session sessionObj, User userObj )
    {
            sessionObj.beginTransaction();
            sessionObj.save(userObj);
            sessionObj.getTransaction().commit();
            sessionObj.clear();
    }

}
