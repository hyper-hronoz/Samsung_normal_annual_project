package com.example.samsungnormalannualproject.Utils;

import com.example.samsungnormalannualproject.Erors.UserErrors.ToastError;

public class InputDataValidator {
    private String login;
    private String password;
    private String password_2;

    private final int MINIMAL_LOGIN_LENGTH = 4;
    private final int MINIMAL_PASSWORD_LENGTH = 8;

    private String errorMessage = "";

    public InputDataValidator(String login, String password, String password_2) {
        this.login = login;
        this.password = password;
        this.password_2 = password_2;
    }

    public InputDataValidator(String login, String password) {
        this.login = login;
        this.password = password;
        this.password_2 = password;
    }

    public String validate() {

        if (this.password.equals(password_2)) {
            if (new FormValidator(this.login).setMinLength(this.MINIMAL_LOGIN_LENGTH)) {

                if (new FormValidator(this.password).setMinLength(this.MINIMAL_PASSWORD_LENGTH))  {
                    return "OK";
                } else {
                     setError("Password minimal length: " + this.MINIMAL_PASSWORD_LENGTH);
                }
            } else {
                 setError("Login minimal length is: " + this.MINIMAL_LOGIN_LENGTH);
            }
        } else {
            setError("Password do not match");
        }

        if (this.errorMessage != "") {
            returnError();
        }

        return returnError();
    }

    public void setError(String error) {
        this.errorMessage += error + "\n ";
    }

    public String returnError() {
        return this.errorMessage;
    }
}
