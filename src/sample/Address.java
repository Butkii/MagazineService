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
public class Address implements Serializable{
    private String streetName;
    private int streetNum;
    private String suburb;
    private int postCode;

    public Address() {
    }

    public Address(String streetName, int streetNum, String suburb, int postCode) {
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.suburb = suburb;
        this.postCode = postCode;
        System.out.println(streetName + streetNum + suburb + postCode);
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Address: " + "Street: " + streetName + " Street Number: " + streetNum 
                + " Suburb: " + suburb + " PostCode: " + postCode + '\n';
    }
}
