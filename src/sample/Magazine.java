/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Magazine implements Serializable{
    private ArrayList<Customer> customers;
    private String name;
    private double weeklyCost;
    private ArrayList<Supplement> supplements;

    /**
     *
     */
    public Magazine() {
        this.customers = new ArrayList<Customer>();
        this.name = "";
        this.weeklyCost = 0.0;
        this.supplements = new ArrayList<Supplement>();
    }

    /**
     *
     * @param name
     * @param weeklyCost
     * @param supplements
     * @param customers
     */
    public Magazine(String name, double weeklyCost, ArrayList<Supplement> supplements, ArrayList<Customer> customers) {
        // this.customers = customers;
        this.weeklyCost = weeklyCost;
        // this.supplements = supplements;
        this.name = name;
    }

    /**
     *
     * @param name
     * @param weeklyCost
     */
    public Magazine(String name, double weeklyCost) {
        this.name = name;
        this.weeklyCost = weeklyCost;
        this.supplements = new ArrayList<Supplement>();
        this.customers = new ArrayList<Customer>();
    }

    /**
     *
     * @return
     */
    public String getName() {    
        return name;
    }
    
    /**
     *
     * @return
     */
    public double getWeeklyCost() {
        return weeklyCost;
    }

    /**
     *
     * @return
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    public ArrayList<String> getListOfCustomers() {
        ArrayList<String> names = new ArrayList<String>();
        int i = 0;
        for (Customer customer : customers) {
            names.add(customer.getName());
        }
        return names;
    }
    
        /**
     *
     * @param name
     * @return
     */
    public Customer getCustomer(String name) {
        for (Customer temp: customers) {
            if (temp.getName().equalsIgnoreCase(name)) {
                return temp;
            }
        }
        return new Customer();
    }

    /**
     *
     * @return
     */
    public ArrayList<Supplement> getListOfSupplements() {
        return supplements;
    }
    
    public ArrayList<String> getSupplementNames() {
        ArrayList<String> names = new ArrayList<String>();
        int i = 0;
        for (Supplement supplement : supplements) {
            names.add(supplement.getName());
            
        }
        return names;
    }
    

    /**
     *
     * @param name
     * @return
     */
    public Supplement getSupplement(String name) {
        for (Supplement temp: supplements) {
            if (temp.getName().equalsIgnoreCase(name)) {
                return temp;
            }
        }
        return new Supplement();
    }

    /**
     *
     * @param s
     */
    public boolean addSupplement(Supplement s) {
        supplements.add(s);
        return true;
    }
    
    /**
     *
     * @param c
     */
    public boolean addCustomer(Customer c) {
        customers.add(c);
        customers.toString();
        return true;
    }

    /**
     *
     * @param weeklyCost
     */
    public void setWeeklyCost(double weeklyCost) {
        if (weeklyCost > 0) {
            this.weeklyCost = weeklyCost;
        }
    }
    
    /**
     *
     * @param name
     */
    public void setName(String name) {
        if (!name.equals("") && name != null) {
            this.name = name;
        }
    }
    
    public boolean addAssociatetoPaying(String paying, String associate) {
        if (getCustomer(paying) instanceof PayingCustomer) {
            ((PayingCustomer) getCustomer(paying)).addAssociateCustomer(associate);
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getPayingCustomers() {
        ArrayList<String> result = new ArrayList<String>();
        for (Customer c: customers) {
            if (c instanceof PayingCustomer) {
                result.add(c.getName());
            }
        }
        return result;
    }
    
    public PayingCustomer getPayingCustomerOfAssociate(String name) {
        PayingCustomer temp = new PayingCustomer();
        ArrayList<String> aC = new ArrayList<String>();
        for (Customer c: customers) {
            if (c instanceof PayingCustomer) {
                aC = ((PayingCustomer) c).getAssociateCustomers();
                if (aC.contains(name)) {
                    return (PayingCustomer) c;
                }
            }
        }
        return temp;
    }
    
    public ArrayList<String> getAssociateCustomersofPaying(String name) {
        for (Customer c: customers) {
            if (c instanceof PayingCustomer) {
                return ((PayingCustomer) c).getAssociateCustomers();
            }
        }
        return new ArrayList<String>();
    }
    
    /**
     *
     * @param name
     * @return
     */
    public boolean removeCustomer(String name) {
        Customer temp = getCustomer(name);
        if (temp.getName().equalsIgnoreCase(name)) {
            if (temp instanceof PayingCustomer) {
                ArrayList<String> toRemove = getAssociateCustomersofPaying(name);
                for (String s: toRemove) {
                    Customer temp1 = getCustomer(s);
                    if(temp.getName().equalsIgnoreCase(name)) {
                        customers.remove(temp1);
                    }
                }
            } else {
                PayingCustomer pC = getPayingCustomerOfAssociate(name);
                int index = customers.indexOf(pC);
                pC.removeAssociateCustomer(name);
                customers.set(index, pC);
            }
            customers.remove(temp);
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean removeSupplement(String name) {
        Supplement temp = getSupplement(name);
        if (temp.getName().equalsIgnoreCase(name)) {
            supplements.remove(temp);
            return true;
        } else {
            return false;
        }
    }
    
    //add customer

    /**
     *
     * @return
     */
    public String toString() {
        String result = "Name: " + name + " Cost: " + weeklyCost + " Supplements: ";
        if (supplements.size() > 0) {
            for (Supplement temp: supplements) {
                result += temp.getName() + " ";
            }
        }
        else {
            result += "None";
        }
        // if (customers.size() > 0) {
        //     result += " Customers: ";
        //     for (Customer temp: customers) {
        //         result += temp.getName() + " ";
        //     }
        // }
        // else {
        //     result += " No customers";
        // }
        result += "\n";
        return result;
    }
    
    public void editSupplement(Supplement s, Supplement temp) {
        int index = supplements.indexOf(s);
        supplements.set(index, temp);
    }
}    

