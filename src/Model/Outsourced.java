/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
/**
 *
 * @author spenc
 */

public class Outsourced extends Part{
    // Variable Declaration
    private String companyName;
    
    
    // Constructor (Has to be first command because of the super() because it's a Java API rule)
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    
   
    // Setter and Getter Methods
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String CompanyName) {
        this.companyName = CompanyName;
    }
    
}
