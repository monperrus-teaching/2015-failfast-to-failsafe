package org.apache.commons.mail;


public class DefaultAuthenticator extends javax.mail.Authenticator {
    private final javax.mail.PasswordAuthentication authentication;

    public DefaultAuthenticator(final java.lang.String userName ,final java.lang.String password) {
        this.authentication = new javax.mail.PasswordAuthentication(userName , password);
    }

    @java.lang.Override
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return this.authentication;
    }
}

