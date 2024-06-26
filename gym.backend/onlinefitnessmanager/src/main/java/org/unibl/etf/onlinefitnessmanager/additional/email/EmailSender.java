package org.unibl.etf.onlinefitnessmanager.additional.email;

public interface EmailSender {
    void send(String to, String email);
    void send(String to, String email, String subject);
    void send(String to, String email, String subject, String from);
}
