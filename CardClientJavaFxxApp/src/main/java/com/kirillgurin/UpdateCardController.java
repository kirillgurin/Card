package com.kirillgurin;

import com.kirillgurin.model.Card;
import com.kirillgurin.repository.CardRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class UpdateCardController {

    public TextField textFieldQuestion;
    public TextField textFieldAnswer;
    private Card card;




    public void buttonSave(ActionEvent actionEvent) {
        String question = this.textFieldQuestion.getText();
        String answer = this.textFieldAnswer.getText();
        CardRepository cardRepository = new CardRepository();
        try {
            if (this.textFieldQuestion.getText().equals("") || this.textFieldAnswer.getText().equals("")){
                App.showAlert("Error", "Textfield question & textfield answer can't be empty!", Alert.AlertType.ERROR);
            }
            else {
                cardRepository.updateCard(question, answer, this.card.getId());
                App.showAlert("Info", "Card successfully updated", Alert.AlertType.INFORMATION);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buttonClose(ActionEvent actionEvent) {
        Stage stage = (Stage) this.textFieldQuestion.getScene().getWindow();
        stage.close();
    }

    public void initData(Card card) {
        this.card = card;
        this.textFieldQuestion.setText(card.getQuestion());
        this.textFieldAnswer.setText(card.getAnswer());
    }
}
