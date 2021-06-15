package session;

import database.DatabaseConnection;
import database.entity.User;
import org.hibernate.Session;

public final class LoadUserData {
    private static LoadUserData loadUserData;
    public static LoadUserData getInstance()
    {
        if(loadUserData==null)
        {
            loadUserData=new LoadUserData();
        }
        return loadUserData;
    }

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

   public User loadUser(String username)
   {
      return sessionObj.get(User.class,username);
   }


}
