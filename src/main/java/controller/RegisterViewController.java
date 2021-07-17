package controller;


import view.AvailableViews;
import view.View;
import model.database.DatabaseConnectionModel;
import model.database.RegisterUserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.hibernate.Session;
import model.validation.UserDataValidationModel;
import model.validation.Valid;

public class RegisterViewController {


    //region Controls
    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfEmail;

    @FXML
    private AnchorPane labelsRoot;

    @FXML
    private ComboBox<String> cbxGender;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private PasswordField tfConfirmPassword;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfUsername;

    @FXML
    private Label lbFirstNameError;

    @FXML
    private Label lbUsernameError;

    @FXML
    private Label lbPhoneNumberError;

    @FXML
    private Label lbLastNameError;

    @FXML
    private Label lbCountryError;

    @FXML
    private Label lbEmailError;

    @FXML
    private Label lbPasswordError;
    //endregion

    private DatabaseConnectionModel databaseConnectionModel;
    private RegisterUserModel registerUserModel;
    private UserDataValidationModel userDataValidationModel;
    private Session session;
    private int errorCount;


    @FXML
    private void initialize() {
        databaseConnectionModel = new DatabaseConnectionModel();
        registerUserModel = new RegisterUserModel();
        userDataValidationModel = new UserDataValidationModel();
        establishConnectionWithDatabase();
        setComboBoxItems();
        setTextFieldsFormatter();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> session = databaseConnectionModel.getSessionObj());
        thread.start();
    }

    private void setComboBoxItems() {
        ObservableList<String> gender = FXCollections.observableArrayList("Men", "Women");
        cbxGender.setItems(gender);
        cbxGender.getSelectionModel().selectFirst();
    }

    private void setTextFieldsFormatter()
    {
        userDataValidationModel.setTextFieldFormatter(tfPhoneNumber,"[0-9]+");
        userDataValidationModel.setTextFieldFormatter(tfFirstName,"[a-zA-Z]+");
        userDataValidationModel.setTextFieldFormatter(tfLastName,"[a-zA-Z]+");
        userDataValidationModel.setTextFieldFormatter(tfCountry,"[a-zA-Z]+");
        userDataValidationModel.setTextFieldFormatter(tfUsername,"^[a-zA-Z0-9]+$");
    }

    @FXML
    private void btnConfirmOnAction(ActionEvent event) {
        resetLabels();
        validate();
        if(errorCount==0)
        {
            registerUser();
            showAlert();
            btnReturnOnAction(null);
        }
    }

    private void validate()
    {
        errorCount=7;
        checkPassword();
        checkPhoneNumber();
        checkFirstAndLastName();
        checkCountry();
        checkUsername();
        checkEmail();
    }

    private void resetLabels()
    {
        for(Node label : labelsRoot.getChildren()) {
            Label labelToReset = (Label)label;
            labelToReset.setText("");
        }
    }

    private void checkPassword()
    {
           String passwordValid = userDataValidationModel.checkPassword(tfPassword.getText(),tfConfirmPassword.getText());
           if(!passwordValid.equals(Valid.VALID))
           {
               showError(lbPasswordError,passwordValid);
           }
           else
           {
               errorCount--;
           }
    }

    private void checkPhoneNumber()
    {
        String phoneNumberValid = userDataValidationModel.checkPhoneNumber(tfPhoneNumber.getText());
        if(!phoneNumberValid.equals(Valid.VALID))
        {
            showError(lbPhoneNumberError,phoneNumberValid);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkFirstAndLastName()
    {
        String firstNameValid = userDataValidationModel.checkFirstName(tfFirstName.getText());
        if(!firstNameValid.equals(Valid.VALID))
        {
            showError(lbFirstNameError,firstNameValid);
        }
        else
        {
            errorCount--;
        }

        String lastNameValid = userDataValidationModel.checkLastName(tfLastName.getText());
        if(!lastNameValid.equals(Valid.VALID))
        {
            showError(lbLastNameError,lastNameValid);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkCountry()
    {
        String rcountryValid= userDataValidationModel.checkCountry(tfCountry.getText());
        if(!rcountryValid.equals(Valid.VALID))
        {
            showError(lbCountryError,rcountryValid);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkUsername()
    {
        String usernameValid= userDataValidationModel.checkUsername(tfUsername.getText(),registerUserModel,session);
        if(!usernameValid.equals(Valid.VALID))
        {
            showError(lbUsernameError,usernameValid);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkEmail()
    {
        String emailValid= userDataValidationModel.checkEmail(tfEmail.getText());
        if(!emailValid.equals(Valid.VALID))
        {
            showError(lbEmailError,emailValid);
        }
        else
        {
            errorCount--;
        }
    }

    private void showError(Label label, String text)
    {
        label.setText(text);
    }

    private void registerUser()
    {
        registerUserModel.register(tfFirstName.getText(),tfLastName.getText(),tfUsername.getText(),tfPhoneNumber.getText(),tfCountry.getText(),tfEmail.getText(),cbxGender.getValue(),tfPassword.getText(),session);
    }

    private void showAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("User successfully registered");
        alert.showAndWait();
    }

    @FXML
    private void btnReturnOnAction(ActionEvent event) {
        databaseConnectionModel.closeConnection(session);
        View.getInstance().setView(View.getView(AvailableViews.LOGIN_VIEW));
    }
}

