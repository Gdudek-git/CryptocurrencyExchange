package controller.account;

import view.AvailableViews;
import view.View;
import model.database.DatabaseConnectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.hibernate.Session;
import model.session.ChangeUserDataModel;
import model.session.LoggedUser;
import model.validation.UserDataValidationModel;
import model.validation.Valid;

public class ChangePasswordViewController {

    //region Controls
    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfConfirmedNewPassword;

    @FXML
    private Label lbCurrentPassword;

    @FXML
    private Label lbNewPassword;
    //endregion


    private ChangeUserDataModel changeUserDataModel;
    private DatabaseConnectionModel databaseConnectionModel;
    private UserDataValidationModel userDataValidationModel;
    private Session session;

    @FXML
    private void initialize() {
        changeUserDataModel = new ChangeUserDataModel();
        databaseConnectionModel = new DatabaseConnectionModel();
        userDataValidationModel = new UserDataValidationModel();
        establishConnectionWithDatabase();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> session=  databaseConnectionModel.getSessionObj());
        thread.start();
    }

    @FXML
    private void btnConfirmOnAction(ActionEvent event) {
        resetLabels();
        if(session!=null)
        {
            if(isCurrentPasswordCorrect())
            {
              tryToChangePassword();
            }
            else
            {
                showMessage(lbCurrentPassword,"Incorrect password");
            }
        }
    }

    private boolean isCurrentPasswordCorrect()
    {
        return pfPassword.getText().equals(LoggedUser.getInstance().getLoggedUser().getPassword());
    }

    @FXML
    private void btnReturnOnAction(ActionEvent event) {
        databaseConnectionModel.closeConnection(session);
        View.getInstance().setView(View.getView(AvailableViews.ACCOUNT_VIEW));
    }

    private void tryToChangePassword()
    {
        String result = userDataValidationModel.checkPassword(pfNewPassword.getText(),pfConfirmedNewPassword.getText());

        if(!result.equals(Valid.VALID))
        {
            showMessage(lbNewPassword,result);
        }
        else
        {
           changePassword();
        }
    }

    private void showMessage(Label label, String message)
    {
        label.setText(message);
    }

    private void changePassword()
    {
        changeUserDataModel.changePassword(LoggedUser.getInstance().getLoggedUser(),pfNewPassword.getText());
        changeUserDataModel.updateLoggedUserData(session);
        showMessage(lbNewPassword,"Changed successfully");
    }

    private void resetLabels()
    {
        lbCurrentPassword.setText("");
        lbNewPassword.setText("");
    }

}
