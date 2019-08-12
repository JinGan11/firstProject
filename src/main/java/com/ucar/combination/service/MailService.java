package com.ucar.combination.service;

public interface MailService {

    void sendMail(String to, String subject, String content);
}
