package model.session;

import model.database.entity.User;

public final class LoggedUser {

    private static LoggedUser loggedUser = new LoggedUser();
    private User user;

    public static LoggedUser getInstance()
    {
        return  loggedUser;
    }

    public User getLoggedUser()
    {
        return user;
    }

    public void setLoggedUser(User user)
    {
        this.user = user;
    }


}
