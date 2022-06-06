/**
 * Danny Chung
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        PhoneBook b = new PhoneBook();
        Sms sms = new Sms();

        UI ui = new UI(reader, b, sms);

        ui.start();
    }
}
