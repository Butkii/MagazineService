/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author User
 */
public class AssociateCustomer extends Customer{
    AssociateCustomer() {
        super();
    }
    
    AssociateCustomer(String name, String email, Address address) {
        super(name, email, address);
    }
    
    public String toString() {
        return super.toString();
    }
    
}    

