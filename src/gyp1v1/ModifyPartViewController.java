/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyp1v1;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
public class ModifyPartViewController implements Initializable {
    
    // Variable Declarations
    @FXML private TextField companyNameField;
    @FXML private Label companyNameLabel;
    @FXML private TextField modifyPartIDField;
    @FXML private TextField modifyPartNameField;
    @FXML private TextField partInvField;
    @FXML private TextField partPriceField;
    @FXML private TextField partMaxField;
    @FXML private TextField partMinField;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourceRadioButton;
    private ToggleGroup modifyToggleGroup;
    private Part selectedPart;
    int partIndex;
    
       // This Method when called will save and change back to the Main Menu
    public void partsModifySaveButtonPushed(ActionEvent event) throws IOException{
        if(partIsValid()){
        // setting the updated information
        selectedPart.setName(modifyPartNameField.getText());
        selectedPart.setPrice(Double.parseDouble(partPriceField.getText()));
        selectedPart.setStock(Integer.parseInt(partInvField.getText()));
        selectedPart.setMin(Integer.parseInt(partMinField.getText()));
        selectedPart.setMax(Integer.parseInt(partMaxField.getText()));
        
        if(this.modifyToggleGroup.getSelectedToggle().equals(this.inHouseRadioButton)){
            updatePartInHouse();
        } 
        else if(this.modifyToggleGroup.getSelectedToggle().equals(this.outsourceRadioButton)) {
            updatePartOutsourced();
        }

        // The throws IOException is for safely reading the fxml document
        Parent AddPartViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene AddPartScene = new Scene(AddPartViewParent);

        // Now we need to get the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(AddPartScene);
        window.show();
        }
    }
    
    // This method will allow us to modify our Outsourced parts to InHouse
    private void updatePartInHouse(){
        Inventory.partToUpdate(new InHouse(Integer.parseInt(modifyPartIDField.getText()), modifyPartNameField.getText(),
                Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()),
                Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()),
                Integer.parseInt(companyNameField.getText())));
    }
    
    // This method will allow us to modify our InHouse parts to Outsourced
    private void updatePartOutsourced(){
        Inventory.partToUpdate(new Outsourced(Integer.parseInt(modifyPartIDField.getText()), modifyPartNameField.getText(),
                Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()),
                Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()),
                companyNameField.getText()));
    }
    
    // This method will populate the Modify Part with the selected attributes
    public void sendPart(Part part){
        
        modifyPartIDField.setText(String.valueOf(part.getId()));
        modifyPartNameField.setText(part.getName());
        partInvField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMinField.setText(String.valueOf(part.getMin()));
        partMaxField.setText(String.valueOf(part.getMax()));
        
        // Determining whether the part is inHouse or Outsourced part
        if(part instanceof InHouse){
            // Set to MachineID and RadioButton
            companyNameLabel.setText("MachineID");
            inHouseRadioButton.selectedProperty().set(true);
            companyNameField.setText(String.valueOf(((InHouse) part).getMachineID()));
        } else {
            // Set to Outsourced and RadioButton
            companyNameLabel.setText("Company Name");
            outsourceRadioButton.selectedProperty().set(true);
            //companyNameField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            companyNameField.setText(((Outsourced) part).getCompanyName());
        }
            selectedPart = part;
            partIndex = selectedPart.getId();
    }

    
    // This method determines the validity of the Part
    private boolean partIsValid(){
        String name = modifyPartNameField.getText();
        String price = partPriceField.getText();
        String companyOrMachine = companyNameField.getText();
        
        // Validate Name
        if(name == null || name.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Part Name");
            alert.setContentText("Please enter a valid Part Name");
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
            alert.setContentText("Please enter a positive double value for Price");
            alert.showAndWait();
            return false;
        }
        // Checking validity of MachineID
        try{
            if(this.modifyToggleGroup.getSelectedToggle().equals(this.inHouseRadioButton)){
                if(companyOrMachine == null || Integer.parseInt(companyOrMachine) == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error: Invalid Machine ID");
                    alert.setContentText("Please enter a valid Machine ID");
                    alert.showAndWait();
                    return false;
                }
            } 
        } catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Invalid Machine ID");
                alert.setContentText("Please enter a valid Machine ID");
                alert.showAndWait();
                return false;
        }
        // Checking validity of CompanyName
        try{
            if(this.modifyToggleGroup.getSelectedToggle().equals(this.outsourceRadioButton)){
                if(companyOrMachine == null || companyOrMachine.length() == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error: Invalid Company Name");
                    alert.setContentText("Please enter a valid Company Name");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Invalid Company Name");
                alert.setContentText("Please enter a valid Company Name");
                alert.showAndWait();
                return false;
        }
        
        int minInv = Integer.parseInt(partMinField.getText());
        int maxInv = Integer.parseInt(partMaxField.getText());
        int inventory = Integer.parseInt(partInvField.getText());
        if(maxInv < minInv || minInv >= maxInv){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Change Max and Min Values");
            alert.setContentText("Change Max value to be greater than min");
            alert.showAndWait();
            return false;
        } else if(maxInv < inventory || minInv > inventory){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Inventory number");
            alert.setContentText("Inventory must be less than max and more than min");
            alert.showAndWait();
            return false;
        }
       return true;
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
    // Method for radioButtons
    public void radioSelection(){
    // Determining if InHouse is selected then we need to change the company name label
        if(this.modifyToggleGroup.getSelectedToggle().equals(this.inHouseRadioButton)){
            companyNameLabel.setText("MachineID");
            companyNameField.setPromptText("MachineID");
        } else if(this.modifyToggleGroup.getSelectedToggle().equals(this.outsourceRadioButton)){
            companyNameLabel.setText("Company Name");
            companyNameField.setPromptText("Company Name");

            } 
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modifyToggleGroup = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(modifyToggleGroup);
        this.outsourceRadioButton.setToggleGroup(modifyToggleGroup);
    }    
    
}
