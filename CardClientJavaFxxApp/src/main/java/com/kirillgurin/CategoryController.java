package com.kirillgurin;

import com.kirillgurin.repository.CategoryRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CategoryController {
    @FXML
    public TextField textFieldNameCategory;


    public void buttonSave(ActionEvent actionEvent) {
        String name = this.textFieldNameCategory.getText();
        CategoryRepository categoryRepository = new CategoryRepository();
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("out.json"));
                String s = bufferedReader.readLine();
                categoryRepository.addCategory(name, Integer.parseInt(s));
                App.showAlert("Info", "New category successfully added to this user", Alert.AlertType.INFORMATION);
                this.textFieldNameCategory.setText("");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void buttonClose(ActionEvent actionEvent) {
        Stage stage = (Stage) this.textFieldNameCategory.getScene().getWindow();
        stage.close();
    }
}
