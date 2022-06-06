/**
 * Danny Chung
 */

import java.io.FileNotFoundException;
import java.util.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;
import org.json.simple.parser.ParseException;

public class UI {
    private PhoneBook phonebook;
    private Scanner reader;
    private Sms sms;
    
    public UI(Scanner reader, PhoneBook phonebook, Sms sms) {
        this.phonebook = phonebook;
        this.reader = reader;
        this.sms = sms;
    }
    
    public void start() {
        // Load contacts.json file first.
        loadJSON();
        
        while (true) {
            // UI Options
            System.out.println("");
            System.out.println("List of Contacts");
            System.out.println("Input an option:");
            System.out.println(" 1 Add a name and number.");
            System.out.println(" 2 Update an address.");
            System.out.println(" 3 Search contact list for a person by phone number.");
            System.out.println(" 4 Search contact list for a phone number by person.");
            System.out.println(" 5 Search contact list for an address by name.");
            System.out.println(" 6 Delete an entry from the contact list.");
            System.out.println(" 7 Alphabetically List or filter all contacts.");
            System.out.println(" 8 Send mass SMS message to all contacts.");
            System.out.println(" x Quit.");
            System.out.println("");
            System.out.print("command: ");
            String command = reader.nextLine();

            switch (command) {
                case "x":
                    // Saves phonebook objects to contacts.json and exits.
                    saveJSON();
                    System.exit(0);
                case "1":
                    addNumber();
                    break;
                case "2":
                    addAddress();
                    break;
                case "3":
                    searchByNumber();
                    break;
                case "4":
                    searchForNumber();
                    break;
                case "5":
                    searchInfo();
                    break;
                case "6":
                    deleteInformation();
                    break;
                case "7":
                    searchKeyWord();
                    break;
                case "8":
                    sendMassSMS();
                    break;
            }
        }
        
    }

    /**
     * Deletes an entry from the phonebook based on name.
     */
    public void deleteInformation() {
        System.out.print("Enter a full name to delete (must be exact match): ");
        String who = reader.nextLine();
        boolean found = false;
        for (Person p : this.phonebook.phonebook) {
            if (p.getName().equals(who)) {
                this.phonebook.deletePerson(p);
                found = true;
                break;
            }
        }
        if (found == false) {
            System.out.println("No entries found.");
        } else {
            System.out.println("Successfully deleted " + who);
        }
    }

    /**
     * Adds an entry to the phonebook based on name and phone number.
     */
    public void addNumber() {
        System.out.print("(If a name already exists in list, will add phone number to " +
                "the person's list of phone numbers)\nName: ");
        String name = reader.nextLine();
        System.out.print("Phone number: ");
        String number = reader.nextLine();
        Person person = new Person(name);

        // If the name exists in phone book, add the number to their list.
        if (this.phonebook.getNames().contains(name)) {
        for (Person p : this.phonebook.phonebook) {
            if (p.getName().contains(name)) {
                p.addNumber(number);
            }
        }
        }

        // Otherwise, create a new person and phone number entry into the phonebook.
        else if (!this.phonebook.getNames().contains(name)) {
            this.phonebook.addPerson(person);
            person.addNumber(number);
        }       
    }

    /**
     * Searches for a partial or full name string in the contact list,
     * and prints the person's name and number if found.
     */
    public void searchForNumber() {
        System.out.print("Enter a name or part of name: ");
        String name = reader.nextLine();
        if (this.phonebook.getNames().contains(name)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(name)) {
                    System.out.print(p.getName() + ": " + p.getNumber() + "\n");
                }
            }
        }
        else if (!this.phonebook.getNames().contains(name)) {
            System.out.println(" No entries found.");
        }
    }

    /**
     * Searches for a partial or full number im the contact list,
     * and prints the person's name and number if found.
     */
    public void searchByNumber() {
        boolean found = false;
        System.out.print("Enter phone number or part of phone number: ");
        String numberCheck = reader.nextLine();
        for (Person p : this.phonebook.phonebook) {
            for (Object numberObj : p.getNumber()) {
                String number = (String) numberObj;
                if (number.contains(numberCheck)) {
                    System.out.println(" " + p.getName());
                    found = true;
                    break;
                }
            }
        }
        if (found == false) {
            System.out.println("No entries found.");
        }
    }

    /**
     * Modifies person's address entry.
     */
    public void addAddress() {
        System.out.print("Name of person to update address (must be exact match): ");
        String name = reader.nextLine();
        System.out.print("Enter address: ");
        String address = reader.nextLine();

        // If person is existing, change their address.
        if (this.phonebook.getNames().contains(name)) {
        for (Person p : this.phonebook.phonebook) {
            if (p.getName().equals(name)) {
                p.setAddress(address);
            }
        }
        }

        // Otherwise, create a new person and set their address.
        else if (!this.phonebook.getNames().contains(name)) {
            Person person = new Person(name);
            this.phonebook.addPerson(person);
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(name)) {
                p.setAddress(address);
            }
            } 
    }
}

    /**
     * Searches contact list based on partial or full name string.
     * Prints their address.
     */
    public void searchInfo() {
        System.out.print("Enter a name or part of name: ");
        String name = reader.nextLine();
        if (this.phonebook.getNames().contains(name)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(name)) {
                    System.out.println(p.getName());
                    System.out.println("  Address: " + p.getAddress());
                    }
                }
            }
        else if (!this.phonebook.getNames().contains(name)) {
            System.out.println("No entries found.");
        }
    }

    /**
     * Searches contact list based on partial or full keyword string,
     * can be either by name or address, but not by number.
     */
    public void searchKeyWord() {
        System.out.print("Search by keyword (leave blank to list all contacts): ");
        String key = reader.nextLine();

        // .toLowerCase to remove case sensitivity for searching.
        key = key.toLowerCase();
        Collections.sort(this.phonebook.phonebook);
        if (this.phonebook.getNames().toLowerCase().contains(key)
                || this.phonebook.getAddresses().toLowerCase().contains(key)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().toLowerCase().contains(key)
                        || p.getAddress().toLowerCase().contains(key)) {
                                        System.out.println(" " + p.getName());
                                        System.out.println("  Address: " + p.getAddress());
                                        
                    if (p.checkPhoneSize() < 1) {
                        System.out.println("  Phone number not found.\n");
                    } else
                        System.out.println("  Phone numbers: " + "\n" + "   " + p.getNumberInfo());
                }
        }
            }
        else if (!this.phonebook.getNames().contains(key)
                || !this.phonebook.getAddresses().contains(key)) {
            System.out.println(" Keyword not found.");
        }
    }

    /**
     * Loads contacts.json and creates phonebook objects based on the file.
     */
    public void loadJSON() {
        // Prepare JSON parser.
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("contacts.json")) {
            Object contactsObj = parser.parse(reader);
            JSONArray contactsList = (JSONArray) contactsObj;

            // For each contact in the contact list.
            int contacts_size = contactsList.size();
            for (int i = 0; i < contacts_size; i++) {

                // Take the contact object with name, number, and
                // address, and make a Person from it.
                JSONObject contactObj = (JSONObject) contactsList.get(i);
                JSONObject contact = (JSONObject) contactObj.get("contact");
                Person person = new Person((String)contact.get("name"));
                for (String nums: (ArrayList<String>)contact.get("number(s)")) {
                    person.addNumber(nums);
                }

                // Add the person to the phonebook and set their address.
                this.phonebook.addPerson(person);
                person.setAddress((String)contact.get("address"));
            }
        }
        catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        catch (IOException err) {
            err.printStackTrace();
        }
        catch (ParseException err) {
            err.printStackTrace();
        }

    }

    /**
     * Saves all contacts in the phonebook to contacts.json,
     * and then outputs the contacts.json file.
     */
    public void saveJSON() {
        // Prepare a JSONArray.
        JSONArray contactList = new JSONArray();

        // For each person in the phonebook.
        for (Person p: this.phonebook.phonebook) {

            // Make a contactDetails object and place the person's address, name, numbers.
            JSONObject contactDetails = new JSONObject();
            contactDetails.put("address", p.getAddress());
            contactDetails.put("name", p.getName());
            contactDetails.put("number(s)", p.getNumber());

            // Place the contact details into an overarching contactObject.
            JSONObject contactObject = new JSONObject();
            contactObject.put("contact", contactDetails);

            // Add the contactObject to the overarching contactList.
            contactList.add(contactObject);
        }

        // Write the contacts.json file.
        try (FileWriter file = new FileWriter("contacts.json")) {
            JsonParser parser = new JsonParser();
            JsonElement formatted_contacts = parser.parse(contactList.toJSONString());

            // Beautify the JSON spacing for readability.
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            file.write(gson.toJson(formatted_contacts));
            file.flush();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * Sends a mass SMS message to all numbers in the contact list.
     */
    public void sendMassSMS() {
        System.out.print("Begin typing your message. It will begin with 'Hi (name here),': ");
        // Message to send.
        String message = reader.nextLine();

        // For each person in the phonebook,
        for (Person p : this.phonebook.phonebook) {

            // Message to send.
            String full_message = "Hi " + p.getName() + ", " + message;

            for (Object numberObj: p.getNumber()) {
                // Person's phone number.
                String number = (String)numberObj;

                // Verify that it is an 11 digit number, and print the SID for confirmation.
                if ((number.length() == 11)) {
                    String sid = this.sms.sendSMS(full_message, (String)number);
                    System.out.println(full_message + " SENT TO " + number + " WITH SID " + sid);
                } else {
                    // Wrong number of digits, invalid number, don't send.
                    System.out.println("Not sent to " + number);
                }

            }
        }
    }
}
