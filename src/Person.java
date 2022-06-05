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
    private String address;
    
    public Person() {
        this.name = "";
        this.phone = new ArrayList<String>();
        this.address = address;
    }
    
    @Override
    public int compareTo(Person person) {
        return this.name.compareToIgnoreCase(person.getName())
                - person.getName().compareToIgnoreCase(this.name);
    }
    
    public Person(String name) {
        this.name = name;
        this.phone = new ArrayList<String>();
        this.address = "Unknown address.";
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
    
    public List getNumber() {
        return this.phone;
    }    
    
    public int checkPhoneSize() {
        return this.phone.size();
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}

