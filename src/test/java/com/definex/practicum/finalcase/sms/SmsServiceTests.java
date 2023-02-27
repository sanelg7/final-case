package com.definex.practicum.finalcase.sms;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.impl.SmsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmsServiceTests {



    private final SmsServiceImpl smsService = new SmsServiceImpl();

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testApprovedApplicationSms() {
        // create a sample credit limit application and user
        User user = new User("12345678901", "John", "Doe", "5551234567", new Date(90, 0, 1));
        CreditLimitApplication application = new CreditLimitApplication(user, true, 5000.0);

        // call the method being tested
        String sms = smsService.approvedApplicationSms(application);

        // assert that the SMS message is correct
        String expectedSms = "Congratulations John Doe! Your credit limit application is approved. You can view your new limit within the banking app, or check with your TCKN and Date of Birth from the application web page. ";
        assertEquals(expectedSms, sms);
    }

    @Test
    public void testUnapprovedApplicationSms() {
        // create a sample credit limit application and user
        User user = new User("12345678901", "John", "Doe", "5551234567", new Date(90, 0, 1));
        CreditLimitApplication application = new CreditLimitApplication(user, true, 5000.0);

        String sms = smsService.unapprovedApplicationSms(application);
        // call the method being tested
        String expectedSms = "John Doe, we are sorry to inform you that your credit limit application is not approved due to low credit score. ";
        assertEquals(expectedSms, sms);
    }

}