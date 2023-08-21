/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class Customer implements Serializable {
    private String name;
    private String email;
    private Address address;

    /**
     *
     */
    public Customer() {
        this.name = "Default";
        this.email = "abc@example.com";
        this.address = new Address();
    }

    /**
     *
     * @param name
     * @param email
     */
    public Customer(String name, String email, Address address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
    
    /**
     *
     * @param name
     */
    public boolean setName(String name) {
        if (name != null && name != "") {
            for (int i = 0; i < name.length(); i++) {
                if (!((name.charAt(i) >= 'a' && name.charAt(i)<= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || name.charAt(i) == ' ')) {
                    System.out.println("Invalid name");
                    return false;
                }
            }
            this.name = name;
            return true;
        } else {
            System.out.println("Invalid name");
            return false;
        }
    }

    /**
     *
     * @param email
     */
    public boolean setEmail(String email) {
        if (email != null && email != "") {
            this.email = email;
            return true;
        } else {
            System.out.println("Invalid email.");
            return false;
        }
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
    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String toString() {
        String result = "Name: " + name + " Email: " + email + "\n" + address.toString();
        return result;
    }
}
