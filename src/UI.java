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

/**
 *
 * @author ffoxx
 */
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
        loadJSON();

        System.out.println("List of Contacts");
        System.out.println("Choose an option:");
        System.out.println(" 1. Add a name and number.");
        System.out.println(" 2 search for a number");
        System.out.println(" 3 search for a person by phone number");
        System.out.println(" 4 add an address");
        System.out.println(" 5 search for personal information");
        System.out.println(" 6 delete personal information");
        System.out.println(" 7 filtered listing");
        System.out.println(" x quit");
        
        while (true) {
            System.out.println("");
            System.out.print("command: ");
            String command = reader.nextLine();
            
            if (command.equals("x")) {
                saveJSON();
                break;
            }
            
            else if (command.equals("1")) {
                addNumber();
            }
            
            else if (command.equals("2")) {
                searchForNumber();
            }
            
            else if (command.equals("3")) {
                searchByNumber();
            }
            
            else if (command.equals("4")) {
                addAddress();
            }
            
            else if (command.equals("5")) {
                searchInfo();
            }
            
            else if (command.equals("6")) {
                System.out.print("whose information: ");
                String who = reader.nextLine();
                for (Person p : this.phonebook.phonebook) {
                    if (p.getName().contains(who)) {
                        this.phonebook.deletePerson(p);
                        break;
                    }
                }
            }
            
            else if (command.equals("7")) {
                searchKeyWord();
            }
        }
        
    }
    
    
    
    public void addNumber() {
        System.out.print("whose number: ");
        String name = reader.nextLine();
        System.out.print("number: ");
        String number = reader.nextLine();
        Person person = new Person(name);
        if (this.phonebook.getNames().contains(name)) {
        for (Person p : this.phonebook.phonebook) {
            if (p.getName().contains(name)) {
                p.addNumber(number);
            }
        }
        }
        else if (!this.phonebook.getNames().contains(name)) {
            this.phonebook.addPerson(person);
            person.addNumber(number);
        }       
    }
    
    public void searchForNumber() {
        System.out.print("whose number: ");
        String name = reader.nextLine();
        if (this.phonebook.getNames().contains(name)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(name)) {
                    System.out.print(" " + p.getNumber());
                }
            }
        }
        else if (!this.phonebook.getNames().contains(name)) {
            System.out.println("  not found");
        }
    }
    
    public void searchByNumber() {
        System.out.print("number: ");
        String number = reader.nextLine();
        if (this.phonebook.getNumbers().contains(number)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getNumber().contains(number)) {
                    System.out.println(" " + p.getName());
                }
            }
        }
        else if (!this.phonebook.getNumbers().contains(number)) {
            System.out.println(" not found");
        }
    }
    
    public void addAddress() {
        System.out.print("whose address: ");
        String name = reader.nextLine();
        System.out.print("address: ");
        String address = reader.nextLine();
        if (this.phonebook.getNames().contains(name)) {
        for (Person p : this.phonebook.phonebook) {
            if (p.getName().contains(name)) {
                p.addAddress(address);
            }
        }
        }
        else if (!this.phonebook.getNames().contains(name)) {
            Person person = new Person(name);
            this.phonebook.addPerson(person);
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(name)) {
                p.addAddress(address);
            }
            } 
    }
}
    
    public void searchInfo() {
        System.out.print("whose information: ");
        String name = reader.nextLine();
        if (this.phonebook.getNames().contains(name)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(name)) {
                    System.out.println(p.getName());
                    if (p.checkAddressSize() < 1) {
                        System.out.println("  address unknown");
                    }
                    else {
                    System.out.println("  address: " + p.getAddress());
                    }
                    if (p.checkPhoneSize() < 1) {
                        System.out.println("  phone number not found");
                    }
                    else
                    System.out.print("  phone number(s): " + "\n" + "    " + p.getNumberInfo());
                }
            }
        }
        else if (!this.phonebook.getNames().contains(name)) {
            System.out.println("  not found");
        }
        
    }
    
    public void searchKeyWord() {
        System.out.print("keyword (if empty, all listed): ");
        String key = reader.nextLine();
        Collections.sort(this.phonebook.phonebook);
        if (this.phonebook.getNames().contains(key)
                || this.phonebook.getAddresses().contains(key)) {
            for (Person p : this.phonebook.phonebook) {
                if (p.getName().contains(key)
                        || p.getAddress().contains(key)) {
                                        System.out.println(" " + p.getName());
                                        System.out.println("  address: " + p.getAddress());
                                        
                    if (p.checkPhoneSize() < 1) {
                        System.out.println("  phone number not found");
                    } else
                        System.out.println("  phone numbers: " + "\n" + "   " + p.getNumberInfo());

                }
        }
            }
        
        else if (!this.phonebook.getNames().contains(key)
                || !this.phonebook.getAddresses().contains(key)) {
            System.out.println(" keyword not found");
        }
    }

    public void loadJSON() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("contacts.json")) {
            Object contactsObj = parser.parse(reader);
            JSONArray contactsList = (JSONArray) contactsObj;
            int contacts_size = contactsList.size();
            for (int i = 0; i < contacts_size; i++) {
                JSONObject contactObj = (JSONObject) contactsList.get(i);
                JSONObject contact = (JSONObject) contactObj.get("contact");
                Person person = new Person((String)contact.get("name"));
                for (String nums: (ArrayList<String>)contact.get("number(s)")) {
                    person.addNumber(nums);
                }
                this.phonebook.addPerson(person);
                person.addAddress((String)contact.get("address"));

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

    public void saveJSON() {
        JSONArray contactList = new JSONArray();

        for (Person p: this.phonebook.phonebook) {
            JSONObject contactDetails = new JSONObject();
            contactDetails.put("address", p.getAddress());
            contactDetails.put("name", p.getName());

            String numbers = p.getNumber();
            String[] number_array = numbers.split("\n");
            List<String> numbersList = Arrays.asList(number_array);
            contactDetails.put("number(s)", numbersList);

            JSONObject contactObject = new JSONObject();
            contactObject.put("contact", contactDetails);

            contactList.add(contactObject);
        }

        try (FileWriter file = new FileWriter("contacts.json")) {
            JsonParser parser = new JsonParser();
            JsonElement formatted_contacts = parser.parse(contactList.toJSONString());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            file.write(gson.toJson(formatted_contacts));
            file.flush();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

}
