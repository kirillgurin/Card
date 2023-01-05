package com.kirillgurin;

import com.kirillgurin.model.Category;
import com.kirillgurin.repository.CategoryRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CategoryChangeCotroller {
    @FXML
    public TextField textFieldNameCategory;
    private Category category;

    public void buttonSave(ActionEvent actionEvent) {
        String name = this.textFieldNameCategory.getText();
        try {
            CategoryRepository categoryRepository = new CategoryRepository();
            categoryRepository.updateCategory(this.category.getId(), name, this.category.getUserId());
            App.showAlert("Info", "Category successfully changed", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buttonClose(ActionEvent actionEvent) {
        Stage stage = (Stage) this.textFieldNameCategory.getScene().getWindow();
        stage.close();
    }

    public void initData(Category category) {
        this.category = category;
        this.textFieldNameCategory.setText(category.getName());
    }
}
