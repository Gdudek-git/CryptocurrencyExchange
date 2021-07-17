package controller;

import view.AvailableViews;
import view.View;
import model.database.DatabaseConnectionModel;
import model.database.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.session.LoginModel;
import org.hibernate.Session;
import model.session.LoadUserModel;


public class LoginViewController {

    //region Controls
    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private Label lbWrongLoginData;
    //endregion

    private LoadUserModel loadUserModel;
    private LoginModel loginModel;
    private DatabaseConnectionModel databaseConnectionModel;
    private Session session;


    @FXML
    private void initialize() {
        loadUserModel = new LoadUserModel();
        databaseConnectionModel = new DatabaseConnectionModel();
        loginModel = new LoginModel();
        establishConnectionWithDatabase();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> session = databaseConnectionModel.getSessionObj());
        thread.start();
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        databaseConnectionModel.closeConnection(session);
        changeView(AvailableViews.REGISTER_VIEW);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {

        User user = loadUserModel.loadUser(tfUsername.getText(),session);
            if (areLoginDataValid(user)) {
                login(user);
            }
            else
            {
                showError("Incorrect username or password");
            }
    }

    private boolean areLoginDataValid(User user)
    {
        return loginModel.areLoginDataValid(user,tfUsername.getText(),tfPassword.getText());
    }

    private void login(User user)
    {
        loginModel.setLoggedUser(user);
        databaseConnectionModel.closeConnection(session);
        changeView(AvailableViews.MAIN_MENU_VIEW);
    }
    private void changeView(String name)
    {
        View.getInstance().setView(View.getView(name));
    }

    private void showError(String error)
    {
        lbWrongLoginData.setText(error);
    }


}