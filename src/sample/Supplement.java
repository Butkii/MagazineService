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
public class Supplement implements Serializable{
    private String name;
    private double weeklyCost;

    Supplement() {
        this.name = "Default";
        this.weeklyCost = 0.0;
    }

    Supplement(String name, double weeklyCost) {
        this.name = name;
        this.weeklyCost = weeklyCost;
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
     * @param name
     */
    public void setName(String name) {
        try {
            if (name.equals("")) {
            this.name = name;
            }
        } catch(NullPointerException e) {
            System.out.println("Name is empty");
        }
    }

    /**
     *
     * @param weeklyCost
     */
    public void setWeeklyCost(double weeklyCost) {
        if (weeklyCost >= 0) {
            this.weeklyCost = weeklyCost;
        }
    }
    
    /**
     *
     * @return
     */
    public String toString() {
        return "Name: " + name + " Cost: " + weeklyCost + " ";
    }
    
}    
