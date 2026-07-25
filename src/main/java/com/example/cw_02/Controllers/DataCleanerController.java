package com.example.cw_02.Controllers;


import com.example.cw_02.classes.DataCleaner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DataCleanerController {

    @FXML
    private Label text;

    @FXML
    void cleanDealers(ActionEvent event) {
        DataCleaner.dealers();
        text.setText("Dealers Legacy File Cleaned");

    }

    @FXML
    void cleanInventory(ActionEvent event) {
        DataCleaner.inventory();
        text.setText("Inventory Legacy File Cleaned");
    }

}













