package com.mohdeveloper.blogplatform.service;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
