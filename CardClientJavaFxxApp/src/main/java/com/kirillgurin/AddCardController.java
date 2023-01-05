package com.kirillgurin;

import com.kirillgurin.model.Category;
import com.kirillgurin.repository.CardRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AddCardController {

    public TextField textfieldQuestion;
    public TextField textfieldAnswer;
    public ComboBox<Category> comboboxCategories;



    public void buttonSave(ActionEvent actionEvent) {
        Category category = this.comboboxCategories.getSelectionModel().getSelectedItem();
        String question = this.textfieldQuestion.getText();
        String answer = this.textfieldAnswer.getText();
        if (question.equals("") || answer.equals("")){
            App.showAlert("Error", "Textfield question & textfield answer can't be empty!", Alert.AlertType.ERROR);
        }
        else{
            CardRepository cardRepository = new CardRepository();
            try {
                cardRepository.addCard(question, answer, category.getId());
                App.showAlert("Info", "Card successfully added", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void buttonClose(ActionEvent actionEvent) {
        Stage stage = (Stage) this.comboboxCategories.getScene().getWindow();
        stage.close();
    }

    public void initdata(ArrayList<Category> categoriesList) {
        this.comboboxCategories.setItems(FXCollections.observableList(categoriesList));
    }
}
