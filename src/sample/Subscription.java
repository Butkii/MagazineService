/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author User
 */
public class Subscription implements Serializable{
    private HashMap<String, ArrayList<Supplement>> sub;

    /**
     *
     */
    public Subscription() {
        sub = new HashMap<String, ArrayList<Supplement>>();
    }
    
    /**
     *
     * @param name
     */
    public void addCustomer(String name) {
        ArrayList<Supplement> sup = new ArrayList<Supplement>();
        sub.put(name, sup);
    }
    
    /**
     *
     * @param name
     * @param s
     */
    public void addCustomer(String name, Supplement s) {
        ArrayList<Supplement> sup = new ArrayList<Supplement>();
        sup.add(s);
        sub.put(name, sup);
    }
    
    /**
     *
     * @param name
     * @return
     */
    public ArrayList<Supplement> getSubscriptions(String name) {
        return sub.get(name);
    }
    
    /**
     *
     * @param name
     * @return
     */
    public boolean findCustomer(String name) {
        return sub.containsKey(name);
    }
    
    /**
     *
     * @param name
     * @param s
     */
    public void addSupplement(String name, Supplement s) {
        if (sub.containsKey(name)) {
            ArrayList<Supplement> temp = sub.get(name);
            temp.add(s);
            sub.put(name, temp);
        }
        else {
            addCustomer(name, s);
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String result = "";
        ArrayList<Supplement> sup;
        for (Map.Entry<String, ArrayList<Supplement>> e : sub.entrySet()){
            result += ("Name: " + e.getKey()
                               + " Supplement: ");
            sup = e.getValue();
            for (Supplement s: sup) {
                result += s.toString();
            }
            result += "\n";
        }
        return result;           
    }
    
    public void editSupplementDetails(Supplement s, Supplement temp) {
        for(ArrayList<Supplement> value: sub.values()) {
            if (value.contains(s)) {
                value.set(value.indexOf(s), temp);
            }
        }
    }
    
    public boolean removeSupplement(Supplement s) {
        for(ArrayList<Supplement> value: sub.values()) {
            if (value.contains(s)) {
                value.remove(s);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeCustomer(Customer c) {
        sub.remove(c);
        return true;
    }
    
    public String getCustomersBySupplement(Supplement s) {
        String result = "";
        ArrayList<Supplement> temp;
        for (String name: sub.keySet()){
            temp = getSubscriptions(name);
            if (temp.contains(s)) {
                result += name + " ";
            }
        }
        return result;        
    }
}
