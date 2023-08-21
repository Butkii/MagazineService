/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class MagazineService implements Serializable {
    
    private Magazine mag;
    private Subscription sub;

    public MagazineService(Magazine magazine, Subscription subscription) {
        this.mag = magazine;
        this.sub = subscription;
    }

    public MagazineService() {
        mag = new Magazine();
        sub = new Subscription();
    }

    public Magazine getMag() {
        return mag;
    }

    public void setMag(Magazine mag) {
        this.mag = mag;
    }

    public Subscription getSub() {
        return sub;
    }

    public void setSub(Subscription sub) {
        this.sub = sub;
    }

    //get all supplements for a customer
    public ArrayList<String> getSupplementsbyCustomer(String name) {
        ArrayList<String> supplements = new ArrayList<String>();
        ArrayList<Supplement> customerSupplements = sub.getSubscriptions(name);

        for(Supplement supplement : customerSupplements) {
            supplements.add(supplement.getName());
        }

        return supplements;
    }

    public String getPayingCustomerString(String name) {
        String payingCustomer = "";

        payingCustomer = mag.getPayingCustomerOfAssociate(name).getName();

        return payingCustomer;
    }
    
    public ArrayList<Supplement> getSupplements() {
        return mag.getListOfSupplements();
    }

    public ArrayList<String> getCustomerNames() {
        ArrayList<String> customerNames = null;
        
        try{
            customerNames = mag.getListOfCustomers();

        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }

        return customerNames;
        
    }

    public ArrayList<String> getSupplementNames() {
        ArrayList<String> supplements = new ArrayList<String>();

        try{
            supplements = mag.getSupplementNames();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return supplements;
    }

    public ArrayList<String> getPayingCusName() {
        ArrayList<String> payingCustomer = null;

        try{
            payingCustomer = mag.getPayingCustomers();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return payingCustomer;
    }
    
    public ArrayList<String> getAssociateByPaying(String name) {
        ArrayList<String> aCustomer = null;

        try{
            aCustomer = mag.getAssociateCustomersofPaying(name);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return aCustomer;
    }
    
    public boolean addAssociatetoPaying(String paying, String associate) {
        mag.addAssociatetoPaying(paying, associate);
        return true;
    }

    public Supplement findSupplement(String supplementName) {
        Supplement supplement = null;
        try{
            supplement = mag.getSupplement(supplementName);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return supplement;
    }

    public void writeMagazineService(File saveFile) {
        try {
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println("Writing to file...");
            out.writeObject(mag);
            out.writeObject(sub);
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void readMagazineService(File openFile) {

        try {
            FileInputStream fileIn = new FileInputStream(openFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            System.out.println("Reading from file...");
            this.mag = (Magazine) in.readObject();
            this.sub = (Subscription) in.readObject();
            in.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        

    }

    public Supplement getSupplement(String supplementName) {
        return mag.getSupplement(supplementName);
    }

    public Customer getCustomer(String customerName) {
        return mag.getCustomer(customerName);
    }


//    public String getBillStrings(int custoewmerId) {
//
//        Customer customer = mag.getCustomerById(customerId);
//        String bill = "";
//        String[] billStrings = Calculation.getWeeklySuppList(customer, sub, mag);
//
//        for (String s : billStrings) {
//            if (s != null) {
//                bill += s + "\n";
//            }
//        }
//
//        return bill;
//    }

    public void deleteSupplement(String s){
        Supplement temp = getSupplement(s);
        mag.removeSupplement(s);
        sub.removeSupplement(temp);
    }

    public void deleteCustomer(String s){
        mag.removeCustomer(s);
    }
    
    public void editSupplement(String currentName, String newName, Double newCost) {
        Supplement curr = getSupplement(currentName);
        Supplement temp = new Supplement(newName, newCost);
        mag.editSupplement(curr, temp);
        sub.editSupplementDetails(curr, temp);
    }
    
    public String findCustomersbySupplement(String suppName) {
        Supplement temp = getSupplement(suppName);
        return sub.getCustomersBySupplement(temp);
    }
    
    public String printMonthlyStatement(Customer c) {
        String email = "";
        double totalCost = mag.getWeeklyCost();
        ArrayList<Supplement> sup;
        
        if (c instanceof PayingCustomer) {
            sup = sub.getSubscriptions(c.getName());

            email += "---------------MONTHLY INVOICE---------------\n\n";
            email += "Weekly Cost: " + mag.getWeeklyCost() + "\n";
            for (Supplement tempS: sup) {
                totalCost += tempS.getWeeklyCost();
                email += "Supplement " + tempS.toString() + "\n";
            }

            for (String aC: ((PayingCustomer) c).getAssociateCustomers()) {
                Customer temp = mag.getCustomer(aC);
                email += "\n\nAssociate Customer " + aC + " :\n";
                sup = sub.getSubscriptions(temp.getName());
                for (Supplement tempS: sup) {
                    totalCost += tempS.getWeeklyCost();
                    email += "Supplement " + tempS.toString() + "\n";
                }
            }
            email += "Total Weekly Cost: " + totalCost + "\n";
            email += "=> Month Total: " + totalCost*4 + "\n\n\n";          
        }        
        System.out.println(email);
        return email;
    }
}
