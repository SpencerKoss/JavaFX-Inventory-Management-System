/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author spenc
 */

public class Inventory {
    // Creating Observable list of allParts and products
    public static int partIDCount;
    public static int productIDCount;
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> products = FXCollections.observableArrayList();
    
    // This method counts and returns the Amount of Parts
    public static int amountOfParts(){        
        for(int i = 0; i < getAllParts().size(); i++){
            partIDCount++;
        }        
        return partIDCount;
    }
    
    // This method counts and returns the Amount of Products
    public static int amountOfProducts(){
        for(int i = 0; i < getAllProducts().size(); i++){
            productIDCount++;
        }
        return productIDCount;
    }

    
    // Clear the contents of the partIDCount
    public static int clearAmountOfParts(){
        return partIDCount = 0;
    }
    
    // Clear the contents of the productIDCount
    public static int clearAmountOfProducts(){
        return productIDCount = 0;
    }
    
    // addPart method passes in an object to add to the allParts list
    public static void addPart(Part p){
        allParts.add(p);
    }
    
    // addProduct method passes in a product object to add to the products list
    public static void addProduct(Product pr){
        products.add(pr);
    }
    
        // Returns matching part using the PartID 
    public static Part findPart(int partID, Part selectedPart){
        for(Part part : getAllParts()){
            if(part.getId() == partID){
                return selectedPart;
            }
        }
        return null; 
    }
    
    // Returns matching part using the PartID 
    public static Part lookupPart(int partID){
        for(Part part : getAllParts()){
            if(part.getId() == partID){
                return part;
            }
        }
        return null; 
    }
    
    // Returns matching product using the ProductID
    public static Product lookupProduct(int productID){
        for(Product product : getAllProducts()){
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }
    
    // Returns matching Part that takes in a string parameter
    public static ObservableList<Part> lookupPart(String partName){
        return allParts;
    }
    
    // Returns matching Product that takes in a string parameter
    public static ObservableList<Product> lookupProduct(String productName){
        return products;
    }
    
    // This method updates the part
    public static void updatePart(int index, Part selectedPart){
        // Getting the correct index in the ArrayList to modify
        for(int i = 0; i < allParts.size(); i++){
            if(index == allParts.indexOf(i)){
                allParts.set(index, selectedPart);
            }   
        }        
    }
    
    public static void partToUpdate(Part part){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == part.id){
                allParts.set(i, part);
                break;
            }
        }
    
    }
    
    // This method updates the product
    public static void updateProduct(int index, Product selectedProduct){
        // Getting the correct index in the ArrayList to modify
        for(int i = 0; i < products.size(); i++){
            if(index == products.indexOf(i)){
                products.set(index, selectedProduct);
            }
        }
    }
    
    // this method removes a selected part and returns true
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }
    
    // This method removes a selected product and returns true
    public static boolean deleteProduct(Product selectedProduct){
        products.remove(selectedProduct);
        return true;
    }
    
    // Returns the list of parts
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    

    
    // Returns the list of Products
    public static ObservableList<Product> getAllProducts(){
        return products;
    }
    

    // Returns the incremented PartID (Auto-Generated)
    public static int incrementPartID(){
        partIDCount++;
        return partIDCount;
    }
    
    // Returns the incremented ProductID (Auto-Generated)
    public static int incrementProductID(){
        productIDCount++;
        return productIDCount;
    }
    
    
}
