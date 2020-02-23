/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyp1v1;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author spenc
 */
public class AddPartViewController implements Initializable {
    
    // Variable Declarations
    private ToggleGroup partToggleGroup;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourceRadioButton;
    @FXML private TextField addPartID;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;
    @FXML private TextField companyNameField;
    @FXML private Label companyNameLabel;
    private int partId;
    boolean selectedRadioButton;


    
   @FXML void onActionSavePart(ActionEvent event) throws IOException{
       // Checking validity of Part before instantiating variables
       if(partIsValid()){
       int id = Inventory.incrementPartID();
       String name = partName.getText();
       int inventory = Integer.parseInt(partInv.getText());
       Double price = Double.parseDouble(partPrice.getText());
       int max = Integer.parseInt(partMax.getText());
       int min = Integer.parseInt(partMin.getText());
       String companyName = companyNameField.getText();
       
        
        if(this.partToggleGroup.getSelectedToggle().equals(this.inHouseRadioButton)){
           int machineID = Integer.parseInt(companyNameField.getText());
           Inventory.addPart(new InHouse(id, name, price, inventory, min, max, machineID));   
        } else {
          Inventory.addPart(new Outsourced(id, name, price, inventory, min, max, companyName));
            }
         
     // Clearing the partIDCount variable
      Inventory.clearAmountOfParts();
      
      // Now we want to be able to go back to the main menu
        Parent AddPartViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene AddPartScene = new Scene(AddPartViewParent);

        // Now we need to get the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(AddPartScene);
        window.show();
       }
   }
    
    @FXML void CancelButtonPushed(ActionEvent event) throws Exception {
        Inventory.clearAmountOfParts();
        
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
    private boolean partIsValid(){
        int min = Integer.parseInt(partMin.getText());
        int max = Integer.parseInt(partMax.getText());
        int inventory = Integer.parseInt(partInv.getText());
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
    
        // Method for radioButtons
    public void radioSelection(){
        // Determining if InHouse is selected then we need to change the company name label
        if(this.partToggleGroup.getSelectedToggle().equals(this.inHouseRadioButton)){
            companyNameLabel.setText("MachineID");
            companyNameField.setPromptText("MachineID");
        } else if(this.partToggleGroup.getSelectedToggle().equals(this.outsourceRadioButton)){
            companyNameLabel.setText("Company Name");
            companyNameField.setPromptText("Company Name");

            } 

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Counting the amount of parts
        Inventory.amountOfParts();
        partToggleGroup = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(partToggleGroup);
        this.outsourceRadioButton.setToggleGroup(partToggleGroup);
        
        // Setting textFields
        partName.setPromptText("Enter Part Name");
        partInv.setPromptText("Enter Inventory Number");
        partPrice.setPromptText("Enter Price");
        partMax.setPromptText("Max");
        partMin.setPromptText("Min");
        companyNameField.setPromptText("Company Name");
        
        // Displaying the next ID Number
        partId = Inventory.partIDCount +1;
        addPartID.setText("Auto-Generated: " + partId);
    }    
    
}
