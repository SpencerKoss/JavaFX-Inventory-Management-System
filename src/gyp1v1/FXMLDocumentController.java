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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 *
 * @author spenc
 */

public class FXMLDocumentController implements Initializable {
    // Declarations - Main Screen
    Stage stage;
    Parent scene;
    private static int index;
    
    // Declarations - Part
    @FXML private TextField partsSearchField;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> inventoryLevelCol;
    @FXML private TableColumn<Part, Double> pricePerUnitCol;
    private static final ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();

    //Declarations - Product
    @FXML private TextField productsSearchField;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn <Product, String> productNameCol;
    @FXML private TableColumn <Product, Integer> productIDCol;
    @FXML private TableColumn<Product, Integer> productInventoryLevelCol;
    @FXML private TableColumn<Product, Double> productPricePerUnitCol; 
    private static final ObservableList<Product> filteredProductsList = FXCollections.observableArrayList(); 
    
    
    public void ExitButtonPushed(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Exit the Program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
         }
    }
        
        // This Method when called will change views to the AddPartView 
    public void AddPartButtonPushed(ActionEvent event) throws IOException{
        Parent AddPartViewParent = FXMLLoader.load(getClass().getResource("AddPartView.fxml"));
        Scene AddPartScene = new Scene(AddPartViewParent);
        
            // Now we need to get the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(AddPartScene);
        window.show();
    }
    
       // This Method when called will change views to the ModifyPartView 
    public void ModifyPartButtonPushed(ActionEvent event) throws IOException{
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPartView.fxml"));
        loader.load();
        ModifyPartViewController MPVCController = loader.getController();
        // sending the selected part from the TableView with the Part data 
        MPVCController.sendPart(partTableView.getSelectionModel().getSelectedItem());
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    
    // This Method when called will change views to the AddProductView 
    public void addProductButtonPushed(ActionEvent event) throws IOException{
        
        Parent AddPartViewParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene AddPartScene = new Scene(AddPartViewParent);
        
        // Now we need to get the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(AddPartScene);
        window.show();
    }
    
    // This Method when called will change views to the ModifyProduct View
    public void ModifyProdutButtonPushed(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();
        ModifyProductController MPVCController = loader.getController();
        // Sending the Product to the Modify Product Screen along with the selected Product Data
        MPVCController.sendProduct(productTableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show(); 
    }
    
    // Deletes a row in the parts Table
    @FXML public void deletePartRowButtonPushed(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the selected Part?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
         partTableView.getItems().removeAll(partTableView.getSelectionModel().getSelectedItem());
        }
    }
    
    // Deletes a row in the product Table
    @FXML public void deleteProductRowButtonPushed(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected Product?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            productTableView.getItems().removeAll(productTableView.getSelectionModel().getSelectedItems());
        }
    }
    // This method will search the TableView using the ID or a String
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
                // Add Alert notifications
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
                    // Add alert notifications
                }
            }
        }
    }
    
    // This method will search the TableView using the ID or a String
    @FXML void searchProducts(ActionEvent event){
        String searchItems = productsSearchField.getText();
        
        // Checking to see if null
        if(searchItems.equals("")){
            // Return the full list
            productTableView.setItems(Inventory.getAllProducts());
        } else {
            // We need a boolean to determine if found
            boolean foundProduct = false;
            try{
            int idNum = Integer.parseInt(searchItems);
            Product product = Inventory.lookupProduct(idNum);
            
            // Found Product
            if(product != null){
                foundProduct = true;
                filteredProductsList.clear();
                filteredProductsList.add(product);
                productTableView.setItems(filteredProductsList);
            }
            // Product Not found
            if(foundProduct == false){
                productTableView.setItems(Inventory.getAllProducts());
                // Add Alert notifications
                }
            }
            catch(NumberFormatException e){
                for(Product product : Inventory.getAllProducts()){
                    if(product.getName().equals(searchItems)){
                        foundProduct = true;
                        filteredProductsList.clear();
                        filteredProductsList.add(product);
                        productTableView.setItems(filteredProductsList);
                    }
                }
                if(foundProduct == false){
                    productTableView.setItems(Inventory.getAllProducts());
                    // Add alert notifications
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Part Table
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // Product Table
        productTableView.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));  
    }   
    
}
