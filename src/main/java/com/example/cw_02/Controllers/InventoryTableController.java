package com.example.cw_02.Controllers;

import com.example.cw_02.classes.InventoryStore;
import com.example.cw_02.classes.PartItem;
import com.example.cw_02.classes.POSCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;

import java.util.ArrayList;
import java.util.List;

public class InventoryTableController {

    @FXML
    private TableView<PartItem> inventoryTable;

    @FXML
    private TableColumn<PartItem, String> colImage;
    @FXML
    private TableColumn<PartItem, String> colCode;
    @FXML
    private TableColumn<PartItem, String> colName;
    @FXML
    private TableColumn<PartItem, String> colBrand;
    @FXML
    private TableColumn<PartItem, String> colCategory;
    @FXML
    private TableColumn<PartItem, Double> colPrice;
    @FXML
    private TableColumn<PartItem, Integer> colStock;
    @FXML
    private TableColumn<PartItem, String> colDate;
    @FXML
    private TableColumn<PartItem, Integer> colStatus;

    @FXML
    private Label totalValueLabel;


    @FXML
    private TextField quantityField;

    private InventoryStore store = new InventoryStore();


    private List<String> cartIds = new ArrayList<>();
    private List<String> cartNames = new ArrayList<>();
    private List<String> cartCategories = new ArrayList<>();
    private List<Double> cartPrices = new ArrayList<>();
    private List<Integer> cartQuantities = new ArrayList<>();
    private List<Integer> cartStockLevels = new ArrayList<>();

    @FXML
    public void initialize() {

        colImage.setCellValueFactory(new PropertyValueFactory<>("imageFile"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        colImage.setCellFactory(column -> new TableCell<PartItem, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String fileName, boolean empty) {
                super.updateItem(fileName, empty);

                if (empty || fileName == null || fileName.equals("No Image") || fileName.trim().isEmpty()) {

                    setGraphic(null);
                    setText(fileName == null || empty ? null : "No Image");
                } else {
                    try {

                        Image img = new Image("file:images/" + fileName, 50, 50, true, true);
                        imageView.setImage(img);


                        setGraphic(imageView);
                        setText(null);
                    } catch (Exception e) {

                        setGraphic(null);
                        setText("Image Missing");
                    }
                }
            }
        });
    }



    @FXML
    void handleLoadInventory(ActionEvent event) {

        ArrayList<PartItem> itemsList = store.viewInventory();
        ObservableList<PartItem> observableItems = FXCollections.observableArrayList(itemsList);
        inventoryTable.setItems(observableItems);
        double total = store.totalMonetaryValue();
        totalValueLabel.setText(String.format("Total Monetary Value: Rs %.2f", total));
    }



    @FXML
    void handleAddToCart(ActionEvent event) {
        PartItem selectedItem = inventoryTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an item from the table first.");
            return;
        }

        int quantity;
        try {
            String qtyText = quantityField.getText();
            // Default to 1 if the field is empty
            if (qtyText == null || qtyText.trim().isEmpty()) {
                quantity = 1;
            } else {
                quantity = Integer.parseInt(qtyText.trim());
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Please enter a valid whole number for the quantity.");
            return;
        }

        if (quantity <= 0) {
            showAlert(Alert.AlertType.WARNING, "Invalid Quantity", "Quantity must be greater than 0.");
            return;
        }

        if (quantity > selectedItem.getStock()) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Stock", "Cannot add more items than currently in stock.");
            return;
        }

        cartIds.add(selectedItem.getPartCode());
        cartNames.add(selectedItem.getName());
        cartCategories.add(selectedItem.getCategory());
        cartPrices.add(selectedItem.getPrice());
        cartQuantities.add(quantity);
        cartStockLevels.add(selectedItem.getStock());

        showAlert(Alert.AlertType.INFORMATION, "Success", quantity + "x " + selectedItem.getName() + " added to cart.");


        quantityField.clear();
    }

    @FXML
    void handleCheckout(ActionEvent event) {
        if (cartIds.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Cart", "Your cart is empty. Please add items before checking out.");
            return;
        }


        String[] ids = cartIds.toArray(new String[0]);
        String[] names = cartNames.toArray(new String[0]);
        String[] categories = cartCategories.toArray(new String[0]);


        double[] prices = new double[cartPrices.size()];
        for (int i = 0; i < cartPrices.size(); i++) prices[i] = cartPrices.get(i);

        int[] quantities = new int[cartQuantities.size()];
        for (int i = 0; i < cartQuantities.size(); i++) quantities[i] = cartQuantities.get(i);

        int[] stocks = new int[cartStockLevels.size()];
        for (int i = 0; i < cartStockLevels.size(); i++) stocks[i] = cartStockLevels.get(i);


        String receipt = POSCart.checkout(ids, names, categories, prices, quantities, stocks);

        if (receipt.startsWith("ERROR!")) {
            showAlert(Alert.AlertType.ERROR, "Checkout Failed", receipt);
        } else {


            for (int i = 0; i < cartIds.size(); i++) {
                String checkedOutId = cartIds.get(i);
                int qtyBought = cartQuantities.get(i);


                for (PartItem item : store.getPartItems()) {
                    if (item.getPartCode().equals(checkedOutId)) {
                        item.setStock(item.getStock() - qtyBought);
                        break; // Move to the next item in the cart
                    }
                }
            }


            store.saveInventory();

            handleLoadInventory(null);


            showAlert(Alert.AlertType.INFORMATION, "Transaction Successful", receipt);
            clearCart();
        }
    }


    private void clearCart() {
        cartIds.clear();
        cartNames.clear();
        cartCategories.clear();
        cartPrices.clear();
        cartQuantities.clear();
        cartStockLevels.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}