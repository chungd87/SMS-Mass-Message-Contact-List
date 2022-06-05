
import java.util.ArrayList;
/**
 *
 * @author ffoxx
 */
public class PhoneBook {
    public ArrayList<Person> phonebook;
    
    public PhoneBook() {
        this.phonebook = new ArrayList<Person>();
    }
    
    public void deletePerson(Person name) {
        this.phonebook.remove(name);
    }
    
    public void addPerson(Person name) {
        this.phonebook.add(name);
    }
    
    public String getNames() {
        String names = "";
        for (Person p : this.phonebook) {
            names = names + p.getName() + " ";
        }
        return names;
    }
    
    public String getNumbers() {
        String numbers = "";
        for (Person p : this.phonebook) {
            numbers = numbers + p.getNumber() + " ";
        }
        return numbers;
    }
    
    public String getAddresses() {
        String addresses = "";
        for (Person p : this.phonebook) {
            addresses = addresses + p.getAddress() + " ";
        }
        return addresses;
    }
}
