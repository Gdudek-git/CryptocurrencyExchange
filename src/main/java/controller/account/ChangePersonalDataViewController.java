package controller.account;

import controller.View;
import model.database.DatabaseConnectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import model.session.ChangeUserDataModel;
import model.session.LoggedUser;
import model.validation.UserDataValidationModel;
import model.validation.Valid;

public class ChangePersonalDataViewController {

    //region Controls

    @FXML
    private TextField tfNewFirstName;

    @FXML
    private TextField tfNewLastName;

    @FXML
    private TextField tfNewNumber;

    @FXML
    private TextField tfNewEmail;

    @FXML
    private TextField tfNewCountry;

    @FXML
    private Label lbIncorrectFirstName;

    @FXML
    private Label lbIncorrectLastName;

    @FXML
    private Label lbIncorrectNumber;

    @FXML
    private Label lbIncorrectEmail;

    @FXML
    private Label lbIncorrectCountry;

    //endregion

    private ChangeUserDataModel changeUserDataModel;
    private UserDataValidationModel userDataValidationModel;
    private DatabaseConnectionModel databaseConnectionModel;
    private Session session;

    @FXML
    private void initialize() {
        changeUserDataModel = new ChangeUserDataModel();
        userDataValidationModel = new UserDataValidationModel();
        databaseConnectionModel = new DatabaseConnectionModel();
        establishConnectionWithDatabase();
        setTextFieldsFormatter();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> session = databaseConnectionModel.getSessionObj());
        thread.start();
    }

    private void setTextFieldsFormatter()
    {
        userDataValidationModel.setTextFieldFormatter(tfNewNumber,"[0-9]+");
        userDataValidationModel.setTextFieldFormatter(tfNewFirstName,"[a-zA-Z]+");
        userDataValidationModel.setTextFieldFormatter(tfNewLastName,"[a-zA-Z]+");
        userDataValidationModel.setTextFieldFormatter(tfNewCountry,"[a-zA-Z]+");
    }

    @FXML
    private void btnChangeCountryOnAction(ActionEvent event) {
        resetLabel(lbIncorrectCountry);
        if(session!=null) {
            if (canChangeData(userDataValidationModel.checkCountry(tfNewCountry.getText()), lbIncorrectCountry)) {
                changeUserDataModel.changeCountry(LoggedUser.getInstance().getLoggedUser(),tfNewCountry.getText());
                updateUserData();
            }
        }
    }

    @FXML
    private void btnChangeEmailOnAction(ActionEvent event) {
            resetLabel(lbIncorrectEmail);
            if(session!=null) {
                if (canChangeData(userDataValidationModel.checkEmail(tfNewEmail.getText()), lbIncorrectEmail)) {
                    changeUserDataModel.changeEmail(LoggedUser.getInstance().getLoggedUser(),tfNewEmail.getText());
                    updateUserData();
                }
            }
    }

    @FXML
    private void btnChangeFirstNameOnAction(ActionEvent event) {
        resetLabel(lbIncorrectFirstName);
        if(session!=null) {
            if (canChangeData(userDataValidationModel.checkFirstName(tfNewFirstName.getText()), lbIncorrectFirstName)) {
                changeUserDataModel.changeFirstName(LoggedUser.getInstance().getLoggedUser(),tfNewFirstName.getText());
                updateUserData();
            }
        }
    }

    @FXML
    private void btnChangeLastNameOnAction(ActionEvent event) {
        resetLabel(lbIncorrectLastName);
        if(session!=null) {
            if (canChangeData(userDataValidationModel.checkLastName(tfNewLastName.getText()), lbIncorrectLastName)) {
                changeUserDataModel.changeLastName(LoggedUser.getInstance().getLoggedUser(),tfNewLastName.getText());
                updateUserData();
            }
        }
    }

    @FXML
    private void btnChangeNumberOnAction(ActionEvent event) {
        resetLabel(lbIncorrectNumber);
        if(session!=null) {
            if (canChangeData(userDataValidationModel.checkPhoneNumber(tfNewNumber.getText()), lbIncorrectNumber)) {
                changeUserDataModel.changePhoneNumber(LoggedUser.getInstance().getLoggedUser(),tfNewNumber.getText());
                updateUserData();
            }
        }
    }

    private boolean canChangeData(String result, Label label)
    {
        if(!result.equals(Valid.VALID))
        {
            showMessage(label,result);
            return false;
        }
        else
        {
            showMessage(label,"Changed succesfully");
            return true;
        }
    }

    private void updateUserData()
    {
        changeUserDataModel.updateLoggedUserData(session);
    }

    private void showMessage(Label label, String message)
    {
        label.setText(message);
    }

    private void resetLabel(Label label)
    {
        label.setText("");
    }

    @FXML
    private void btnReturnOnAction(ActionEvent event) {
        databaseConnectionModel.closeConnection(session);
        View.getInstance().setView(View.getView("AccountView.fxml"));
    }
}
