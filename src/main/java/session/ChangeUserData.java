package session;

import database.DatabaseConnection;
import database.entity.User;
import org.hibernate.Session;

public class ChangeUserData {


    Session sessionObj;

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

    public void changeUserData()
    {
        if(sessionObj!=null) {
            sessionObj.beginTransaction();
            sessionObj.update(LoggedUser.getInstance().getLoggedUser());
            sessionObj.getTransaction().commit();
            sessionObj.clear();
        }
    }

    public void changeSelectedUserData(User user)
    {
        if(sessionObj!=null) {
            sessionObj.beginTransaction();
            sessionObj.update(user);
            sessionObj.getTransaction().commit();
            sessionObj.clear();
        }
    }



}
