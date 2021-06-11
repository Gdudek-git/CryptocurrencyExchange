package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import session.ChangeUserData;
import session.LoggedUser;
import validation.UserDataValidation;

import static ui.controllers.MainStage.getScene;

public class ChangePasswordViewController {

    //region Controllers
    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfConfirmedNewPassword;

    @FXML
    private Label lbIncorrectCurrentPassword;

    @FXML
    private Label lbIncorrectNewPassword;
    //endregion


    ChangeUserData changeUserData = new ChangeUserData();
    UserDataValidation userDataValidation = new UserDataValidation();
    boolean connectedSuccessfully=false;


    @FXML
    private void initialize() {
        establishConnectionWithDatabase();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> {changeUserData.establishConnection();
        connectedSuccessfully=true;
        });
        thread.start();
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        resetLabels();
        if(connectedSuccessfully)
        {
            if(isCurrentPasswordCorrect())
            {
              tryToChangePassword();
            }
            else
            {
                showError(lbIncorrectCurrentPassword,"Incorrect password");
            }
        }
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        changeUserData.closeConnection();
        getMainStage().setScene(getScene("AccountView.fxml"));
    }

    private void tryToChangePassword()
    {
        String result = userDataValidation.checkPassword(pfNewPassword.getText(),pfConfirmedNewPassword.getText());

        if(!result.equals("valid"))
        {
            showError(lbIncorrectNewPassword,result);
        }
        else
        {
            LoggedUser.getInstance().getLoggedUser().setPassword(pfNewPassword.getText());
            changeUserData.changeUserData();
            showChangedSuccessfullyMessage();
        }
    }


    private MainStage getMainStage() {
        return MainStage.getInstance();
    }

    private boolean isCurrentPasswordCorrect()
    {
        if(pfPassword.getText().equals(LoggedUser.getInstance().getLoggedUser().getPassword()))
        {
            return true;
        }
        return false;
    }

    private void showError(Label label, String message)
    {
        label.setText(message);
    }


    private void showChangedSuccessfullyMessage()
    {
        lbIncorrectNewPassword.setText("Changed successfully");
    }

    private void resetLabels()
    {
        lbIncorrectCurrentPassword.setText("");
        lbIncorrectNewPassword.setText("");
    }

}
