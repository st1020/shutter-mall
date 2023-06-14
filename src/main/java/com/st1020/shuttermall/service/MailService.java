package com.st1020.shuttermall.service;

public interface MailService {
    void sendMail(String to, String subject, String content);
}
