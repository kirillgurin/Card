package com.kirillgurin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillgurin.model.User;
import com.kirillgurin.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AutificationController {
    @FXML
    public Label labelLogin;
    @FXML
    public Label labelPassword;
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;

    public void buttonLogIn(ActionEvent actionEvent) {
        String login = this.textFieldLogin.getText();
        String password = this.textFieldPassword.getText();
        if (!login.equals("")){
            try {
                UserRepository userRepository = new UserRepository();
                User user = userRepository.checkUser(login, password);
                //TODO в json сохранить id поьзователя
                if (user != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(new File("out.json"), user.getId());
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "main1.fxml"
                            )
                    );
                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setScene(
                            new Scene(loader.load())
                    );
                    stage.show();
                }
                Stage stage = (Stage) this.textFieldLogin.getScene().getWindow();
                stage.close();
            } catch (IllegalArgumentException e) {
                App.showAlert("Error", "Can not find user in list!", Alert.AlertType.ERROR);
            }catch (IOException e){
                App.showAlert("Error", "For this user categories not found", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
        else {
            App.showAlert("Error", "Field login can't be empty", Alert.AlertType.ERROR);
        }
    }

    public void buttonRegistration(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registration.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(
                    new Scene(fxmlLoader.load())
            );
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void buttonDelete(ActionEvent actionEvent) {
        String login = this.textFieldLogin.getText();
        String password = this.textFieldPassword.getText();
        if (!login.equals("")){

            try {
                UserRepository userRepository = new UserRepository();
                User user = userRepository.checkUser(login, password);
                if (user != null){
                    userRepository.deleteUser(user.getId());
                    App.showAlert("Info", "User successfully deleted", Alert.AlertType.INFORMATION);
                }
                this.textFieldLogin.setText("");
                this.textFieldPassword.setText("");
            } catch (IllegalArgumentException e) {
                this.textFieldLogin.setText("");
                this.textFieldPassword.setText("");
                App.showAlert("Error", "Can not find user in list!", Alert.AlertType.ERROR);
            } catch (IOException e) {
                App.showAlert("Error", "", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
        else {
            App.showAlert("Error", "Field login can't be empty", Alert.AlertType.ERROR);
        }
    }
}
