package com.frietec.sendmail;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class SMTPAuthenticator
extends Authenticator {
    private String username;
    private String password;

    SMTPAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.username, this.password);
    }
}