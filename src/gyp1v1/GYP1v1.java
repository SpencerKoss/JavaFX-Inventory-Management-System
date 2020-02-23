/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyp1v1;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author spenc
 */
public class GYP1v1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Instantiating inHouse & Outsourced Parts objects (Main Screen)
       InHouse inHousePart1 = new InHouse(1, "Processor", 199.99, 10, 1, 100, 1);
       InHouse inHousePart2 = new InHouse(2, "MotherBoard", 149.99, 5, 1, 100, 2);
       Outsourced outSourced1 = new Outsourced(3, "Power Supply", 99.99, 4, 3, 10, "ABD");
       Outsourced outSourced2 = new Outsourced(4, "RAM", 129.99, 2, 1, 11, "ABC");
       
       // Instantiating product objects (Main Screen)
       Product product1 = new Product(1, "Pre-Build PC", 999.99, 4, 1, 5);
       Product product2 = new Product(2, "Toaster", 199.99, 4, 2, 6); 
       Product product3 = new Product(3, "Blender", 229.99, 5, 3, 7); 

       // Adding parts to allParts observable list
       Inventory.addPart(inHousePart1);
       Inventory.addPart(inHousePart2);
       Inventory.addPart(outSourced1);
       Inventory.addPart(outSourced2);

       // Adding products to products observable list
       Inventory.addProduct(product1);
       Inventory.addProduct(product2);
       Inventory.addProduct(product3);
       
       // Adding Associated Parts to Products 
       product1.addAssociatePart(inHousePart1);
       product1.addAssociatePart(inHousePart2);
       product1.addAssociatePart(outSourced1);
       product1.addAssociatePart(outSourced2);
       product2.addAssociatePart(inHousePart2);
       product3.addAssociatePart(outSourced1);
        launch(args);
    }

}
