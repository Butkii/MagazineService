/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class PayingCustomer extends Customer{
    private String bank_account;
    private String card_type;
    private ArrayList<String> associateCustomers;
    
    PayingCustomer() {
        super();
        this.bank_account = "None";
        this.card_type = "None";
        this.associateCustomers = new ArrayList<>();
    }
    
    PayingCustomer(String name, String email, Address address) {
        super(name, email, address);
        this.bank_account = "None";
        this.card_type = "None";
        this.associateCustomers = new ArrayList<>();
    }

    PayingCustomer(String name, String email, Address address, String bank_account, String card_type) {
        super(name, email, address);
        this.bank_account = bank_account;
        this.card_type = card_type;
        this.associateCustomers = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public String getPayment_method() {
        return "Bank Account: " + bank_account + ", Card Type: " + card_type;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getAssociateCustomers() {
        return associateCustomers;
    }

    /**
     *
     * @param bank_account
     * @param card_type
     * @return
     */
    public boolean setPaymentMethod(String bank_account, String card_type) {
        if (bank_account.length() == 12){
            for (int i = 0; i < bank_account.length(); i++) {
                if (!(bank_account.charAt(i) >= '0' && bank_account.charAt(i) <= '9')) {
                    System.out.println("Invalid bank acocunt");
                    return false;
                }
            }
            this.bank_account = bank_account;    
        }
        if (card_type.equalsIgnoreCase("Debit Card") || card_type.equalsIgnoreCase("Credit Card")){
            this.card_type = card_type;  
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *
     * @param customers
     */
    public void setAssociateCustomers(ArrayList<String> customers) {
        this.associateCustomers = customers;
    }
    
    /**
     *
     * @param aC
     */
    public void addAssociateCustomer(String aC) {
        this.associateCustomers.add(aC);
    } 
   
    public void removeAssociateCustomer(String aC) {
        this.associateCustomers.remove(aC);
    }
    
    @Override
    public String toString() {
        String result = super.toString();
        result += getPayment_method() + " Associate Customer: ";
        for (String name: associateCustomers) {
            result += name + " ";
        }
        result += "\n";
        return result;

    }
    
}
