package com.example.cw_02.Controllers;

import javafx.event.ActionEvent;

import com.example.cw_02.classes.InventoryStore;
import com.example.cw_02.classes.PartItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class MainController {

    @FXML
    private TableView<PartItem> inventoryTable;

    @FXML
    private TableColumn<PartItem, String> colCode;
    @FXML
    private TableColumn<PartItem, String> colName;
    @FXML
    private TableColumn<PartItem, String> colBrand;
    @FXML
    private TableColumn<PartItem, Double> colPrice;
    @FXML
    private TableColumn<PartItem, Integer> colStock;
    @FXML
    private TableColumn<PartItem, String> colCategory;
    @FXML
    private TableColumn<PartItem, String> colImage;
    @FXML
    private TableColumn<PartItem, Integer> colStatus;

    private InventoryStore inventoryStore = new InventoryStore();

    @FXML
    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("imageFile"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadInventoryData();
    }

    public void loadInventoryData() {
        ObservableList<PartItem> items = FXCollections.observableArrayList(inventoryStore.viewInventory());
        inventoryTable.setItems(items);
    }

    @FXML
    public void openAddItemsWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cw_02/add-item-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Item");
            stage.setScene(new Scene(root));

            stage.showAndWait();
            loadInventoryData();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading View");
            alert.setContentText("Could not load view: add-item-view.fxml");
            alert.showAndWait();
        }
    }

    public void openDeleteItemsWindow(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cw_02/delete-items-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Item from Inventory");
            stage.setScene(new Scene(root));

            stage.showAndWait();
            loadInventoryData();
        }catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading View");
            alert.setContentText("Could not load view: delete-item-view.fxml");
            alert.showAndWait();
        }
    }

    // 2. MAKE SURE THIS METHOD IS PUBLIC AND USES javafx.event.ActionEvent
    @FXML
    public void promptToAddItems(ActionEvent event) {
        openAddItemsWindow(event);
    }

    @FXML
    public void promptToDeleteItems(ActionEvent event){
        openDeleteItemsWindow(event);
    }

}