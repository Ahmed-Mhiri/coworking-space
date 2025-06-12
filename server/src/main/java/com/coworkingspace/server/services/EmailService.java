package com.coworkingspace.server.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}

