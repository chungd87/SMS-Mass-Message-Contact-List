/**
 * Danny Chung
 */

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Sms {
    public static final String ACCOUNT_SID = "(insert account ID here)";
    public static final String AUTH_TOKEN = "(insert token here)";

    public static final String TWILIO_FROM_NUMBER = "(insert number here)";

    /**
     * Sends 'message_to_send' to 'phone_number'.
     * Requires ACCOUNT_SID and AUTH_TOKEN from Twilio.
     * Requires a Twilio source phone number from TWILIO_FROM_NUMBER
     * Returns SID.
     */
    public static String sendSMS(String message_to_send, String phone_number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(phone_number),
                new PhoneNumber(TWILIO_FROM_NUMBER),
                message_to_send).create();
        return message.getSid();
    }
}
