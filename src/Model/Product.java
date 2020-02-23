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
public class Product {
    // Variable Declarations
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public Product(){
        this.id = 0;
        this.name = "";
        this.price = 0.0;
        this.stock = 0;
        this.min = 0;
        this.max = 0;
    }
    
    // Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public void addAssociatePart(Part selectedAsPart){
        associatedParts.add(selectedAsPart);
    }
    
     public boolean deleteAssociatePart(Part selectedAsPart){
        associatedParts.remove(selectedAsPart);
        return true;
    }
   
    public void setAssociatedParts(ObservableList<Part> selectedAsPart) {
        associatedParts = selectedAsPart;
    }
      
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
