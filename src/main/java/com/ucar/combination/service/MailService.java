package com.ucar.combination.service;

import com.ucar.combination.common.Result;

public interface MailService {

    Result sendMail(String to, String subject, String content);
}
