/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyp1v1;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author spenc
 */


public class ModifyProductController implements Initializable {
    // Variable Declarations
    @FXML private TextField productID;
    @FXML private TextField productName;
    @FXML private TextField productInv;
    @FXML private TextField productPrice;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
    
    @FXML private TextField partsSearchField;
    private int indexID;
    private Product selectedProduct;
    private int productId;
    
    // allParts observableList (Non associated)
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> inventoryLevelCol;
    @FXML private TableColumn<Part, Double> pricePerUnitCol;
    private static final ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
    
    // associatedParts ObservableList
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIDCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartInventory;
    @FXML private TableColumn<Part, Double> associatedPartPriceCol;
    private ObservableList<Part> currentAddParts = FXCollections.observableArrayList();
    
    public void sendProduct(Product product){
        // Setting Fields
        productID.setText("Auto-Generated: " + String.valueOf(product.getId()));
        productName.setText(product.getName());
        productInv.setText(String.valueOf(product.getStock()));
        productPrice.setText(String.valueOf(product.getPrice()));
        productMax.setText(String.valueOf(product.getMax()));
        productMin.setText(String.valueOf(product.getMin()));
        
        selectedProduct = product;
        currentAddParts = selectedProduct.getAllAssociatedParts();
        associatedPartTableView.setItems(currentAddParts);
    }
    
    // Add a Part to the associatedParts List
    public void addPartsPushed(){
        // Setting parts to the selected Part in the TableView
        Part parts = partTableView.getSelectionModel().getSelectedItem();
        if(currentAddParts.contains(parts)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error: Please enter a valid Part");
           alert.setContentText("Cannot add duplicate Parts");
           alert.showAndWait();
        } else {
        currentAddParts.add(parts);
        associatedPartTableView.setItems(currentAddParts);
        }
    }
    
    // Delete a Part from the associatedParts List
    @FXML public void deletePartPushed(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected Associated Part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            associatedPartTableView.getItems().removeAll(associatedPartTableView.getSelectionModel().getSelectedItems());
        }
    }
    
    // This Method when called will save and go back to the main menu
    public void ModifyProductSaveButtonPushed(ActionEvent event) throws IOException{
        if(productIsValid() == true){
        selectedProduct.setName(productName.getText());
        selectedProduct.setStock(Integer.parseInt(productInv.getText()));
        selectedProduct.setPrice(Double.parseDouble(productPrice.getText()));
        selectedProduct.setMax(Integer.parseInt(productMax.getText()));
        selectedProduct.setMin(Integer.parseInt(productMin.getText()));
        
        selectedProduct.setAssociatedParts(currentAddParts);
        if(currentAddParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Please select a part");
            alert.setContentText("Please select at least one Part for this Product");
            alert.showAndWait();
        } else {
        Inventory.updateProduct(indexID, selectedProduct);
        
        Parent AddPartViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene AddPartScene = new Scene(AddPartViewParent);
        
        // Now we need to get the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(AddPartScene);
        window.show();
            }
        }
    }
    
    public void CancelButtonPushed(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to go to the Main Menu?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){ 
            Parent AddPartViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene AddPartScene = new Scene(AddPartViewParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(AddPartScene);
            window.show();
        }
}
        // This method determines the validity of Min, Max, and Inventory
        private boolean productIsValid(){
        String name = productName.getText();
        String price = productPrice.getText();     
        
        // Validate Name
        if(name == null || name.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Product Name");
            alert.setContentText("Please enter a valid Product Name");
            alert.showAndWait();
            return false;
        }
        // Validate Price
        else if(price  == null || price.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Price");
            alert.setContentText("Please enter a valid (Double) price");
            alert.showAndWait();
            return false;
        } 
        // Converting price to Double and checking validity
        try{
            if(Double.parseDouble(price) < 0.0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Price");
            alert.setContentText("Please enter a positive double value for Price");
            alert.showAndWait();
            return false;
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Price");
            alert.setContentText("Please change the Product Price");
            alert.showAndWait();
            return false;
           
        }
        // Now we need to validate the Product price vs the associatedParts price
        try{
            double prodPrice = Double.parseDouble(price);
            double associatedPartsPrice = 0;
            for(Part parts : currentAddParts){
                associatedPartsPrice += parts.getPrice();
            }
            if(associatedPartsPrice > prodPrice){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Invalid Product Price");
                alert.setContentText("Change the Product Price to be greater than the combined Price of the Part");
                alert.showAndWait();
                return false;
                }
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Invalid Product Price");
                alert.setContentText("Change the Product Price");
                alert.showAndWait();
            }
        int min = Integer.parseInt(productMin.getText());
        int max = Integer.parseInt(productMax.getText());
        int inventory = Integer.parseInt(productInv.getText());
        if(max < min || min >= max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Change Max and Min Values");
            alert.setContentText("Change Max value to be greater than min");
            alert.showAndWait();
            return false;
        } else if(max < inventory || min > inventory){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Inventory number");
            alert.setContentText("Inventory must be less than max and more than min");
            alert.showAndWait();
            return false;
        }
       return true;
    }
      
    
        @FXML void searchParts(ActionEvent event){
        String searchItems = partsSearchField.getText();
        
        // Checking to see if null
        if(searchItems.equals("")){
            // Return the full list
            partTableView.setItems(Inventory.getAllParts());
        } else {
            // We need a boolean to determine if found
            boolean foundPart = false;
            try{
            int idNum = Integer.parseInt(searchItems);
            Part part = Inventory.lookupPart(idNum);
            
            // Found part
            if(part != null){
                foundPart = true;
                filteredPartsList.clear();
                filteredPartsList.add(part);
                partTableView.setItems(filteredPartsList);
            }
            // Part Not found
            if(foundPart == false){
                partTableView.setItems(Inventory.getAllParts());
                }
            }
            catch(NumberFormatException e){
                for(Part part : Inventory.getAllParts()){
                    if(part.getName().equals(searchItems)){
                        foundPart = true;
                        filteredPartsList.clear();
                        filteredPartsList.add(part);
                        partTableView.setItems(filteredPartsList);
                    }
                }
                if(foundPart == false){
                    partTableView.setItems(Inventory.getAllParts());
                }
            }
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // AllParts Table
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // associatedParts Table
        associatedPartIDCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory((new PropertyValueFactory<>("stock")));
        associatedPartPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));
        
        associatedPartTableView.setItems(currentAddParts);
    }    
    
}
