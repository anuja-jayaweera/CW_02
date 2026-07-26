package com.example.cw_02.Controllers;

import com.example.cw_02.classes.RandomDealers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class DealerSelectionController {

    @FXML
    private TableColumn<Dealer, String> colContact;

    @FXML
    private TableColumn<Dealer, String> colId;

    @FXML
    private TableColumn<Dealer, String> colLocation;

    @FXML
    private TableColumn<Dealer, String> colName;

    @FXML
    private TableView<Dealer> dealerTable;

    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    void handleSelection(ActionEvent event) {
        List<String> rawDealers = RandomDealers.selectDealers();

        if (rawDealers == null || rawDealers.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No dealers found or text file is empty.");
            return;
        }

        List<Dealer> dealerList = new ArrayList<>();
        for (String line : rawDealers) {
            String[] data = line.split("\\|", -1);

            // Extract data based on the structure ID|Name|Contact|Location[cite: 13]
            String id = data.length > 0 ? data[0].trim() : "Unknown";
            String name = data.length > 1 ? data[1].trim() : "Unknown";
            String contact = data.length > 2 ? data[2].trim() : "Unknown";
            String location = data.length > 3 ? data[3].trim() : "Unknown";

            dealerList.add(new Dealer(id, name, location, contact));
        }

        // 3. Populate the TableView
        ObservableList<Dealer> observableDealers = FXCollections.observableArrayList(dealerList);
        dealerTable.setItems(observableDealers);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Dealer{
        private String id;
        private String name;
        private String location;
        private String contact;

        public Dealer(String id, String name, String location, String contact){
            this.id = id;
            this.name = name;
            this.location = location;
            this.contact = contact;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getContact() {
            return contact;
        }
    }
}
