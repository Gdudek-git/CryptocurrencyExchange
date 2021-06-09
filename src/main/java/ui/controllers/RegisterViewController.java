package ui.controllers;


import database.RegisterUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import validation.UserDataValidation;

import static ui.controllers.MainStage.getScene;
public class RegisterViewController {


    //region Controllers
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

    private RegisterUser registerUser = new RegisterUser();
    private UserDataValidation userDataValidation;
    private int errorCount;


    @FXML
    private void initialize() {
        userDataValidation=new UserDataValidation();
        establishConnectionWithDatabase();
        setComboBoxItems();
        setTextFieldsFormatter();
    }

    private void establishConnectionWithDatabase()
    {
        Thread thread = new Thread(() -> registerUser.establishConnection());
        thread.start();
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        registerUser.closeConnection();
        changeScene();
    }

    private void setComboBoxItems() {
        ObservableList<String> gender = FXCollections.observableArrayList("Men", "Women");
        cbxGender.setItems(gender);
        cbxGender.getSelectionModel().selectFirst();
    }

    private void setTextFieldsFormatter()
    {
        userDataValidation.setTextFieldFormatter(tfPhoneNumber,"[0-9]+");
        userDataValidation.setTextFieldFormatter(tfFirstName,"[a-zA-Z]+");
        userDataValidation.setTextFieldFormatter(tfLastName,"[a-zA-Z]+");
        userDataValidation.setTextFieldFormatter(tfCountry,"[a-zA-Z]+");
        userDataValidation.setTextFieldFormatter(tfUsername,"^[a-zA-Z0-9]+$");
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        resetLabels();
        validate();
        if(errorCount==0)
        {
            registerUser();
            showAlert();
            changeScene();
        }
    }

    private void showAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("User successfully registered");
        alert.showAndWait();
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

    private void checkPassword()
    {
           String result = userDataValidation.checkPassword(tfPassword.getText(),tfConfirmPassword.getText());
           if(!result.equals("valid"))
           {
               showError(lbPasswordError,result);
           }
           else
           {
               errorCount--;
           }
    }

    private void checkPhoneNumber()
    {
        String result = userDataValidation.checkPhoneNumber(tfPhoneNumber.getText());
        if(!result.equals("valid"))
        {
            showError(lbPhoneNumberError,result);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkFirstAndLastName()
    {
        String result= userDataValidation.checkFirstName(tfFirstName.getText());
        if(!result.equals("valid"))
        {
            showError(lbFirstNameError,result);
        }
        else
        {
            errorCount--;
        }
        result= userDataValidation.checkLastName(tfLastName.getText());
        if(!result.equals("valid"))
        {
            showError(lbLastNameError,result);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkCountry()
    {
        String result= userDataValidation.checkCountry(tfCountry.getText());
        if(!result.equals("valid"))
        {
            showError(lbCountryError,result);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkUsername()
    {
        String result= userDataValidation.checkUsername(tfUsername.getText(),registerUser);
        if(!result.equals("valid"))
        {
            showError(lbUsernameError,result);
        }
        else
        {
            errorCount--;
        }
    }

    private void checkEmail()
    {
        String result= userDataValidation.checkEmail(tfEmail.getText());
        if(!result.equals("valid"))
        {
            showError(lbEmailError,result);
        }
        else
        {
            errorCount--;
        }
    }

    private void registerUser()
    {
        registerUser.register(tfFirstName.getText(),tfLastName.getText(),tfUsername.getText(),tfPhoneNumber.getText(),tfCountry.getText(),tfEmail.getText(),cbxGender.getValue(),tfPassword.getText());
    }

    private MainStage getMainStage() {
        return MainStage.getInstance();
    }

    private void changeScene()
    {
        getMainStage().setScene(getScene("LoginView.fxml"));
    }

    private void showError(Label label, String text)
    {
        label.setText(text);
    }

    private void resetLabels()
    {
        for(Node label : labelsRoot.getChildren()) {
            Label labelToReset = (Label)label;
            labelToReset.setText("");
        }
    }





}

