import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.ArrayList;

public class Sms {
    public static final String ACCOUNT_SID = "AC3db24de7dca34632f06702757cdad7e1";
    public static final String AUTH_TOKEN = "1a593aa79f2a8f88f80688caab889214";

    public static void sendSMS(String message_to_send, String phone_number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(phone_number),
                new PhoneNumber("19472254192"),
                message_to_send).create();
        System.out.println(message.getSid());
    }
}
