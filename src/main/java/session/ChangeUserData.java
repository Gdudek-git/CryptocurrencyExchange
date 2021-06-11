package session;

import com.mysql.cj.log.Log;
import database.DatabaseConnection;
import database.entity.User;
import org.hibernate.Session;

public class ChangeUserData {

    DatabaseConnection databaseConnection;
    Session sessionObj;

    public void establishConnection()
    {
        databaseConnection = DatabaseConnection.getInstance();
        sessionObj =  databaseConnection.getSessionObj();
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



}
