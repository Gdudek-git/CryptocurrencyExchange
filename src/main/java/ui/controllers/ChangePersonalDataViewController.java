package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import session.ChangeUserData;
import session.LoggedUser;
import validation.UserDataValidation;
import validation.Valid;

import static ui.controllers.MainStage.getScene;

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

    ChangeUserData changeUserData = new ChangeUserData();
    UserDataValidation userDataValidation = new UserDataValidation();
    boolean connectedSuccessfully=false;

    @FXML
    private void initialize() {
        establishConnectionWithDatabase();
        setTextFieldsFormatter();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> {changeUserData.establishConnection();
            connectedSuccessfully=true;
        });
        thread.start();
    }

    @FXML
    void btnChangeCountryOnAction(ActionEvent event) {
        resetLabel(lbIncorrectCountry);
        if(connectedSuccessfully) {
            if (canChangeData(userDataValidation.checkCountry(tfNewCountry.getText()), lbIncorrectCountry)) {
                LoggedUser.getInstance().getLoggedUser().getUserContact().setCountry(tfNewCountry.getText());
                changeData();
            }
        }
    }

    @FXML
    void btnChangeEmailOnAction(ActionEvent event) {
            resetLabel(lbIncorrectEmail);
            if(connectedSuccessfully) {
                if (canChangeData(userDataValidation.checkEmail(tfNewEmail.getText()), lbIncorrectEmail)) {
                    LoggedUser.getInstance().getLoggedUser().getUserContact().setEmail(tfNewEmail.getText());
                    changeData();
                }
            }
    }

    @FXML
    void btnChangeFirstNameOnAction(ActionEvent event) {
        resetLabel(lbIncorrectFirstName);
        if(connectedSuccessfully) {
            if (canChangeData(userDataValidation.checkFirstName(tfNewFirstName.getText()), lbIncorrectFirstName)) {
                LoggedUser.getInstance().getLoggedUser().setFirstName(tfNewFirstName.getText());
                changeData();
            }
        }
    }

    @FXML
    void btnChangeLastNameOnAction(ActionEvent event) {
        resetLabel(lbIncorrectLastName);
        if(connectedSuccessfully) {
            if (canChangeData(userDataValidation.checkLastName(tfNewLastName.getText()), lbIncorrectLastName)) {
                LoggedUser.getInstance().getLoggedUser().setLastName(tfNewLastName.getText());
                changeData();
            }
        }
    }

    @FXML
    void btnChangeNumberOnAction(ActionEvent event) {
        resetLabel(lbIncorrectNumber);
        if(connectedSuccessfully) {
            if (canChangeData(userDataValidation.checkPhoneNumber(tfNewNumber.getText()), lbIncorrectNumber)) {
                LoggedUser.getInstance().getLoggedUser().getUserContact().setPhoneNumber(tfNewNumber.getText());
                changeData();
            }
        }
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        changeUserData.closeConnection();
        getMainStage().setScene(getScene("AccountView.fxml"));
    }


    private boolean canChangeData(String result, Label label)
    {
        if(!result.equals(Valid.VALID))
        {
            showError(label,result);
            return false;
        }
        else
        {
            showChangedSuccessfullyMessage(label);
            return true;
        }
    }

    private void changeData()
    {
        changeUserData.changeUserData();
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }

    private void showError(Label errorLabel, String message)
    {
        errorLabel.setText(message);
    }

    private void showChangedSuccessfullyMessage(Label label)
    {
        label.setText("Changed successfully");
    }

    private void resetLabel(Label errorLabel)
    {
            errorLabel.setText("");
    }


    private void setTextFieldsFormatter()
    {
        userDataValidation.setTextFieldFormatter(tfNewNumber,"[0-9]+");
        userDataValidation.setTextFieldFormatter(tfNewFirstName,"[a-zA-Z]+");
        userDataValidation.setTextFieldFormatter(tfNewLastName,"[a-zA-Z]+");
        userDataValidation.setTextFieldFormatter(tfNewCountry,"[a-zA-Z]+");
    }
}
