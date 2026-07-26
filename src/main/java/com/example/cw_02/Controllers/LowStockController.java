package com.example.cw_02.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import com.example.cw_02.classes.LowStock;

public class LowStockController {

    @FXML
    private TextArea resultArea;

    @FXML
    void handleLsm(ActionEvent event) {
        String result = LowStock.lsm();
        resultArea.setText(result);
    }
}
