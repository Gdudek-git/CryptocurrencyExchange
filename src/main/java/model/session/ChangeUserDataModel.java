package model.session;

import model.database.entity.User;
import org.hibernate.Session;

public class ChangeUserDataModel {

    public void updateLoggedUserData(Session sessionObj)
    {
        if(sessionObj!=null) {
            sessionObj.beginTransaction();
            sessionObj.update(LoggedUser.getInstance().getLoggedUser());
            sessionObj.getTransaction().commit();
            sessionObj.clear();
        }
    }

    public void updateSelectedUserData(User user, Session sessionObj)
    {
        if(sessionObj!=null) {
            sessionObj.beginTransaction();
            sessionObj.update(user);
            sessionObj.getTransaction().commit();
            sessionObj.clear();
        }
    }

    public void changePassword(User user, String data)
    {
        user.setPassword(data);
    }
    public void changeFirstName(User user, String data)
    {
        user.setFirstName(data);
    }
    public void changeLastName(User user, String data)
    {
        user.setLastName(data);
    }
    public void changeEmail(User user, String data)
    {
        user.getUserContact().setEmail(data);
    }
    public void changeCountry(User user, String data)
    {
        user.getUserContact().setCountry(data);
    }
    public void changePhoneNumber(User user, String data)
    {
        user.getUserContact().setPhoneNumber(data);
    }
}
