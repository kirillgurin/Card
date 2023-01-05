package com.kirillgurin;

import com.kirillgurin.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistrationController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public TextField textFieldName;
    @FXML
    public Label labelLogin;

    public void buttonSave(ActionEvent actionEvent) {
        String login = this.textFieldLogin.getText();
        String password = this.textFieldPassword.getText();
        String name = this.textFieldName.getText();
        if (login != null && !login.equals("") && password != null && name != null){
            try {
                UserRepository userRepository = new UserRepository();
                userRepository.addUser(login, password, name);
                App.showAlert("Info", "User successfully added", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) this.textFieldLogin.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                App.showAlert("Error", "User with login " + login + " already registered!", Alert.AlertType.ERROR);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            App.showAlert("Error", "Field login can't be empty, enter login!", Alert.AlertType.ERROR);
        }
    }
}
