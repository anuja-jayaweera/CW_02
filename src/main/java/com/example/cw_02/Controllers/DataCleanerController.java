package com.example.cw_02;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;

public class HelloController {

    @FXML
    private Label selection;

    @FXML
    void cleanDealers(ActionEvent event) {
        DataCleaner.dealers();
        selection.setText("Dealers file has been loaded");

    }

    @FXML
    void cleanInventory(ActionEvent event) {
        DataCleaner.inventory();
        selection.setText("Inventory file has been loaded");

    }


}

