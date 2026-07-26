package com.example.cw_02.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private void openWindow( String fxmlFile, String title){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cw_02/" + fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("ERROR! Could not load the window: " + title);
        }
    }

    @FXML
    public void openAddItem(ActionEvent event){
        openWindow("add-item-view.fxml","Add new Item");
    }

    @FXML
    public void openDeleteItem(ActionEvent event){
        openWindow("delete-items-view.fxml","Delete Item");
    }

    @FXML
    public void openUpdateItem(ActionEvent event){
        openWindow("update-item-view.fxml","Update Item Details");
    }

    @FXML
    public void openDataCleaner(ActionEvent event){
        openWindow("data-cleaner-view.fxml","Clean Legacy text files");}

    @FXML
    public void openSearchWindow(ActionEvent event){
        openWindow("search-view.fxml","Multi Criteria Search");}

    @FXML
    public void openViewInventory(ActionEvent event){
        openWindow("Inventory-table-view.fxml","Inventory");}

    @FXML
    public void openDealerSelection(ActionEvent event){
        openWindow("random-dealers-view.fxml","Random Dealer Selection");}

    @FXML
    public void openLowStock(ActionEvent event){
        openWindow("low-stock-view.fxml","Low Stock Monitoring");}

}