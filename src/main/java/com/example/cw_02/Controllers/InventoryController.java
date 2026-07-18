package com.example.cw_02.Controllers;

import com.example.cw_02.classes.InventoryManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;

public class InventoryController {

    @FXML
    private TextField codeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField brandField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField imageField;

    @FXML
    private TextField thresholdField;

    @FXML
    public void initialize() {
        categoryComboBox.getItems().addAll("Engine", "Breaks", "Bodywork", "Electrical", "General");
        categoryComboBox.setValue("General");
    } // Fixed: Added the missing closing bracket for the initialize method!

    // this method runs when the button is clicked
    @FXML
    void handleAddItems(ActionEvent event) {
        String code = codeField.getText() != null ? codeField.getText().trim() : "";
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String brand = brandField.getText() != null ? brandField.getText().trim() : "Unknown";
        String priceRaw = priceField.getText() != null ? priceField.getText().trim() : "";
        String qtyraw = quantityField.getText() != null ? quantityField.getText().trim() : "";
        String category = categoryComboBox.getValue();
        String thresholdraw = thresholdField.getText() != null ? thresholdField.getText().trim() : "10";
        String image = imageField.getText() != null && !imageField.getText().isEmpty() ? imageField.getText().trim() : "No Image";

        String date = "No Date";
        if (datePicker.getValue() != null) {
            date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        // input validations
        if (code.isEmpty() || name.isEmpty() || priceRaw.isEmpty() || qtyraw.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Code, Name, Price, Quantity cannot be empty");
            return;
        }

        try {
            double price = Double.parseDouble(priceRaw);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Price should be a valid positive number");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyraw);
            int threshold = Integer.parseInt(thresholdraw);
            if (qty < 0 || threshold < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation error", "Quantity and threshold must be valid positive numbers");
            return;
        }

        String result = InventoryManager.addItem(code, name, brand, priceRaw, qtyraw, category, date, image, thresholdraw);
        if (result.startsWith("ERROR!")) {
            showAlert(Alert.AlertType.ERROR, "Failed to add item", result);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Success", result);
            clearForm();
        }
    }

    // helper method 1
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // helper method 2
    private void clearForm() {
        codeField.clear();
        nameField.clear();
        brandField.clear();
        priceField.clear();
        quantityField.clear();
        categoryComboBox.setValue("General");
        datePicker.setValue(null);
        if (imageField != null) imageField.clear();
        thresholdField.clear();
    }
}