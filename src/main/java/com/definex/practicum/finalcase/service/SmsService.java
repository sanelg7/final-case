package com.definex.practicum.finalcase.service;

import com.definex.practicum.finalcase.model.CreditLimitApplication;

public interface SmsService {
    void sendSms(CreditLimitApplication creditLimitApplication, boolean approved);

    String approvedApplicationSms(CreditLimitApplication creditLimitApplication);

    String unapprovedApplicationSms(CreditLimitApplication creditLimitApplication);
}
