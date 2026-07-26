package com.example.cw_02.Controllers;
import com.example.cw_02.classes.MultiCriteriaSearch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SearchController {

    @FXML
    private TextField keywordField;

    @FXML
    private TextField maxPriceField;

    @FXML
    private TextField minPriceField;

    @FXML
    private TextArea resultsArea;

    @FXML
    private ComboBox<String> searchCategoryBox;

    @FXML
    public void initialize(){
        if(searchCategoryBox != null){
            searchCategoryBox.getItems().addAll("Engine","Breaks","BodyWork","Electrical");
            searchCategoryBox.setValue("General");
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String category = searchCategoryBox.getValue();
        String minPriceRaw = minPriceField.getText() != null ? minPriceField.getText().trim():"";
        String maxPriceRaw = maxPriceField.getText() != null ? maxPriceField.getText().trim(): "";
        String keyword = keywordField.getText() != null ? keywordField.getText().trim() : "";

        resultsArea.clear();

        if(category == null || category.isEmpty() || minPriceRaw.isEmpty() || maxPriceRaw.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill out all fields to perform search");
            return;
        }

        double minPrice = 0.0;
        double maxPrice = 0.0;
        try{
            minPrice = Double.parseDouble(minPriceRaw);
            maxPrice = Double.parseDouble(maxPriceRaw);
            if(minPrice<0 || maxPrice<0 || minPrice>maxPrice){
                showAlert(Alert.AlertType.WARNING,"Validation Error","Prices cannot be negative and Minimum Price cannot be greater then the Maximum Price");
                return;
            }
        } catch (NumberFormatException e){
            showAlert(Alert.AlertType.WARNING,"Validation Error","Prices should be valid numbers");
            return;
        }
        String results = MultiCriteriaSearch.performSearch(category,minPrice,maxPrice,keyword);
        resultsArea.setText(results);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

