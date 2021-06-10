package session;

import database.DatabaseConnection;
import database.entity.User;
import org.hibernate.Session;

public class LoadUserData {

    DatabaseConnection databaseConnection;
    Session sessionObj;
    User user;

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

   public User loadUser(String username)
   {
      return sessionObj.get(User.class,username);
   }


}
