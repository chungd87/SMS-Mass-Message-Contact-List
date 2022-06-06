/**
 * Danny Chung
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Person implements Comparable<Person>{
    private String name;
    private List<String> phone;
    private String address;
    
    public Person() {
        this.name = "";
        this.phone = new ArrayList<String>();
        this.address = address;
    }

    // Override comparator for Person object, alphabetized.
    @Override
    public int compareTo(Person person) {
        return this.name.compareToIgnoreCase(person.getName())
                - person.getName().compareToIgnoreCase(this.name);
    }

    // Default initializer just takes name, and default to unknown address.
    public Person(String name) {
        this.name = name;
        this.phone = new ArrayList<String>();
        this.address = "Unknown address.";
    }

    /**
     * Add a phone number to person's phone number list.
     */
    public void addNumber(String number) {
        this.phone.add(number);
    }

    /**
     * Return person's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     *  Return a string list of phone numbers for easy .contains comparison.
     */
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

    /**
     * Return the number of phone number in Person's phone number list.
     */
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

