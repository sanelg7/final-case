package com.definex.practicum.finalcase.service.impl;

import com.definex.practicum.finalcase.model.CreditLimitApplication;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.service.SmsService;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    // This method only mimics sending sms. Sms consists of subject, receiver and content.
    @Override
    public void sendSms(CreditLimitApplication creditLimitApplication, boolean approved) {
        User user = creditLimitApplication.getUser();
        String receiverGsmNumber = user.getGsmNumber();
        String subject = "About Your Credit Limit Application";
        String content;
        if(approved){
            content = approvedApplicationSms(creditLimitApplication);
        } else {
            content = unapprovedApplicationSms(creditLimitApplication);
        }
        System.out.println("To: " + receiverGsmNumber + "\nSubject: " + subject + "\nContent: " + content);
    }

    @Override
    public String approvedApplicationSms(CreditLimitApplication creditLimitApplication) {
        String fullName = creditLimitApplication.getUser().getFirstName() + " " + creditLimitApplication.getUser().getLastName();
        String sms = "Congratulations " + fullName +"! Your credit limit application is approved. You can view your new limit within the banking app, or check with your TCKN and Date of Birth from the application web page. ";
        return sms;
    }

    @Override
    public String unapprovedApplicationSms(CreditLimitApplication creditLimitApplication) {
        String fullName = creditLimitApplication.getUser().getFirstName() + creditLimitApplication.getUser().getLastName();
        String sms = fullName + ", we are sorry to inform you that your credit limit application is not approved due to low credit score. ";
        return sms;
    }
}
