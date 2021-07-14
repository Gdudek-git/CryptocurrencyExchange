package model.session;

import model.database.entity.User;
import org.hibernate.Session;

public class LoadUserModel {
   public User loadUser(String username, Session sessionObj)
   {
      return sessionObj.get(User.class,username);
   }
}
