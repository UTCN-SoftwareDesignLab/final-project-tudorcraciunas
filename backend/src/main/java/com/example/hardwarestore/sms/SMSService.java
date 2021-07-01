package com.example.hardwarestore.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SMSService {
    private final String ACCOUNT_SID = "AC751e403a577255f36417c61f220c9a8f";

    private final String AUTH_TOKEN = "8fd3d9f10b9fc6487add70983655a5df";

    private final String FROM_NUMBER = "+18326694952";

    public void send(SMSModel sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(sms.getReciever()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
                .create();

    }

}