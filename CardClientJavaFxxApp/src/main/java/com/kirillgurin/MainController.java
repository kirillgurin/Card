package com.kirillgurin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillgurin.model.Card;
import com.kirillgurin.model.Category;
import com.kirillgurin.repository.CardRepository;
import com.kirillgurin.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

public class MainController {
    @FXML
    public ListView<Category> listViewCategories;
    @FXML
    public ListView<Card> listViewCards;
    private int idUser;
    private int idCategory;
    @FXML
    void initialize() throws IOException {
        //TODO загрузить сохраненный id
        this.init();
    }

    public void init() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("out.json"))){
            String id = bufferedReader.readLine();
            this.idUser = Integer.parseInt(id);
            CategoryRepository categoryRepository = new CategoryRepository();
            ArrayList<Category> categories = categoryRepository.getCategoryByIdUser(this.idUser);
            this.listViewCategories.setItems(FXCollections.observableList(categories));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException ignored){}
    }

    public void buttonChooseCategory(ActionEvent actionEvent) throws IOException {
        try {
            Category category = this.listViewCategories.getSelectionModel().getSelectedItem();
            this.idCategory = category.getId();
            CardRepository cardRepository = new CardRepository();
            ArrayList<Card> cards = cardRepository.getCards(category.getId());
            this.listViewCards.setItems(FXCollections.observableList(cards));
        } catch (NullPointerException e) {
            App.showAlert("Error", "There is no category selected", Alert.AlertType.ERROR);
            this.listViewCards.setItems(FXCollections.observableList(new ArrayList<>()));
        }catch (IllegalArgumentException e){
            App.showAlert("Error", "There is no cards for this category", Alert.AlertType.ERROR);
            this.listViewCards.setItems(FXCollections.observableList(new ArrayList<>()));
        }
    }

    public void buttonClose(ActionEvent actionEvent) {
        Stage stage = (Stage) this.listViewCards.getScene().getWindow();
        stage.close();
    }


    public void buttonAddCategory(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(
                    new Scene(fxmlLoader.load())
            );
            stage.showAndWait();
            this.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void buttonChangeCategory(ActionEvent actionEvent) {
        Category category = this.listViewCategories.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("categoryChange.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(
                    new Scene(fxmlLoader.load())
            );
            CategoryChangeCotroller controller = fxmlLoader.getController();
            controller.initData(category);
            stage.showAndWait();
            this.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }catch (NullPointerException e){
            App.showAlert("Error", "There is no category selected", Alert.AlertType.ERROR);
        }
    }

    public void buttonDeleteCategory(ActionEvent actionEvent) {
        Category category = this.listViewCategories.getSelectionModel().getSelectedItem();
        CategoryRepository categoryRepository = new CategoryRepository();
        try {
            categoryRepository.deleteCategory(category.getId());
            App.showAlert("Info", "Category successfully deleted", Alert.AlertType.INFORMATION);
            this.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            App.showAlert("Error", "There is no category selected", Alert.AlertType.ERROR);
        }catch (IllegalArgumentException e){
            this.listViewCategories.setItems(FXCollections.observableList(new ArrayList<>()));
        }
    }


    public void buttonAddCard(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addCard.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        CategoryRepository categoryRepository = new CategoryRepository();
        CardRepository cardRepository = new CardRepository();
        try {
            stage.setScene(
                    new Scene(fxmlLoader.load())
            );
            AddCardController controller = fxmlLoader.getController();
            ArrayList<Category> categoriesList = categoryRepository.getCategoryByIdUser(this.idUser);
            controller.initdata(categoriesList);
            stage.showAndWait();
            ArrayList<Card> cards = cardRepository.getCards(this.idCategory);
            this.listViewCards.setItems(FXCollections.observableList(cards));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }catch (IllegalArgumentException e){
            this.listViewCards.setItems(FXCollections.observableList(new ArrayList<>()));
        }
    }

    public void buttonUpdateCard(ActionEvent actionEvent) {
        Card card = this.listViewCards.getSelectionModel().getSelectedItem();
        if (card == null){
            App.showAlert("Error", "There is no card selected! Choose card", Alert.AlertType.ERROR);
        }
        else {

            CardRepository cardRepository = new CardRepository();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateCard.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.setScene(
                        new Scene(fxmlLoader.load())
                );
                UpdateCardController controller = fxmlLoader.getController();
                controller.initData(card);
                stage.showAndWait();
                ArrayList<Card> cards = cardRepository.getCards(this.idCategory);
                this.listViewCards.setItems(FXCollections.observableList(cards));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void buttonDeleteCard(ActionEvent actionEvent) {
        Card card = this.listViewCards.getSelectionModel().getSelectedItem();
        try {
            CardRepository cardRepository = new CardRepository();
            cardRepository.deleteCard(card.getId());
            App.showAlert("Info", "Card successfully deleted", Alert.AlertType.INFORMATION);
            ArrayList<Card> cards = cardRepository.getCards(this.idCategory);
            this.listViewCards.setItems(FXCollections.observableList(cards));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){
            App.showAlert("Error", "Card not selected! Choose card", Alert.AlertType.ERROR);
        }
        catch (IllegalArgumentException e){
            this.listViewCards.setItems(FXCollections.observableList(new ArrayList<>()));
        }
    }

    public void buttonCloseForm(ActionEvent actionEvent){
        ObjectMapper objectMapper = new ObjectMapper();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("autification.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(
                    new Scene(fxmlLoader.load())
            );
            stage.show();
            objectMapper.writeValue(new File("out.json"), "");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
