package model.validation;

import model.database.RegisterUserModel;
import model.database.entity.User;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.hibernate.Session;

import java.util.function.UnaryOperator;

public class UserDataValidationModel {



   private String isPhoneNumberValid(String phoneNumber)
    {
           if (phoneNumber.length() > 8&&phoneNumber.length()<12) {
               return "valid";
           }
           return "Enter valid number";
    }


    private String isValidLength(String text)
    {
        if(text.length()<3)
        {
            return"Number of characters must exceed 3";
        }

        if(text.length()>15)
        {
            return"Number of characters must not exceed 15";
        }
        return "valid";
    }


    public String checkPhoneNumber(String phoneNumber)
    {
        if(!phoneNumber.equals(""))
        {
           return isPhoneNumberValid(phoneNumber);
        }
        return emptyFieldError();
    }

    public String checkPassword(String password, String confirmPassword)
    {
        if(!password.equals(""))
        {
            if(password.equals(confirmPassword)) {
                return isPasswordValid(password);
            }
            else
            {
                return "Passwords must be the same";
            }
        }
        return emptyFieldError();
    }

    public String checkFirstName(String firstName)
    {
        if(!firstName.equals(""))
        {
            return isValidLength(firstName);

        }
        return emptyFieldError();
    }

    public String checkLastName(String lastName)
    {
        if(!lastName.equals("")) {
            return isValidLength(lastName);

        }
        return emptyFieldError();
    }

    public String checkUsername(String username, RegisterUserModel registerUserModel,Session session)
    {
        if(!username.equals("")) {
            String result = isValidLength(username);
            if(result.equals( Valid.VALID))
            {
                result = checkIfUsernameIsInDatabase(username, registerUserModel,session);
            }
            return result;
        }
        return emptyFieldError();
    }

    public String checkIfUsernameIsInDatabase(String username, RegisterUserModel registerUserModel, Session session)
    {

        User user =  session.get(User.class, username);
        session.clear();
        if(user ==null)
        {
            return Valid.VALID;
        }
        else
        {
            return"That username is taken";
        }
    }

    public String checkCountry(String country)
    {
        if(!country.equals("")) {
            return isValidLength(country);
        }
        return emptyFieldError();
    }

    public String checkEmail(String email)
    {
        if(!email.equals(""))
        {
           return isEmailValid(email);
        }
        return emptyFieldError();
    }

    private String isPasswordValid(String password)
    {
        boolean validLength = false;
        boolean containsDigit = false;
        boolean containsUppercase= false;

        if(password.length()>5&&password.length()<16)
        {
            validLength = true;
        }

        if(password.matches(".*[0-9].*"))
        {
            containsDigit = true;
        }

        if(password.matches(".*[A-Z].*"))
        {
            containsUppercase = true;
        }

        if(validLength&&containsDigit&&containsUppercase)
        {
            return Valid.VALID;
        }
        return "Password must have at least one uppercase, digit and 6 to 16 characters";
    }

    private String isEmailValid(String email)
    {

        if(email.matches("^[a-z0-9+_.-]+@[a-z.-]+$"))
        {
            if(email.chars().filter(d->d=='.').count()>1)
            {
                return "Enter valid email";
            }
            return Valid.VALID;
        }
        return "Enter valid email";
    }

    private String emptyFieldError()
    {
        return "Field cannot be empty";
    }

    public void setTextFieldFormatter(TextField textField, String regex)
    {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String input = change.getText();
            if (input.matches(regex)) {
                return change;
            }
            if(input.isEmpty())
            {
                return change;
            }
            return null;
        };
        textField.setTextFormatter(new TextFormatter<String>(filter));
    }

}
