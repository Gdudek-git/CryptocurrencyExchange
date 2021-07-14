package model.session;

import model.database.entity.User;

public class LoginModel {


    public boolean areLoginDataValid(User user, String username, String password)
    {
        return user != null && user.getPassword().equals(password) && user.getUsername().equals(username);
    }

    public void setLoggedUser(User user)
    {
        LoggedUser.getInstance().setLoggedUser(user);
    }
}
