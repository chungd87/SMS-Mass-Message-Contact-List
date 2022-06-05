/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ffoxx
 */
public class Person implements Comparable<Person>{
    private String name;
    private List<String> phone;
    private ArrayList<Address> address;
    
    public Person() {
        this.name = "";
        this.phone = new ArrayList<String>();
        this.address = new ArrayList<Address>();
    }
    
    @Override
    public int compareTo(Person person) {
        return this.name.compareToIgnoreCase(person.getName())
                - person.getName().compareToIgnoreCase(this.name);
    }
    
    public Person(String name) {
        this.name = name;
        this.phone = new ArrayList<String>();
        this.address = new ArrayList<Address>();
    }
    
    public void addNumber(String number) {
        this.phone.add(number);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNumberInfo() {
        if (checkPhoneSize() < 1) {
            return "no saved numbers";
        }
        String numbers = "";
        for (String num : this.phone) {
            numbers = numbers + num + "\n" + "   ";
            }
        return numbers;
    }
    
    public String getNumber() {
        if (checkPhoneSize() < 1) {
            return "no saved numbers";
        }
        String numbers = "";
        for (String num : this.phone) {
            numbers = numbers + num + "\n";
        }
        return numbers;
    }    
    
    public int checkPhoneSize() {
        return this.phone.size();
    }
    
    public int checkAddressSize() {
        return this.address.size();
    }
    
    public String getAddress() {
        if (checkAddressSize() < 1) {
            return "address unknown";
        }
        String address = "";
        for (Address add : this.address) {
            address = address + add.getAddress();
        }
        return address;
    }
    
    public void addAddress(String address) {
        Address add = new Address(address);
        this.address.add(add);
    }
}

