package org.apache.commons.mail.mocks;


public class MockEmailConcrete extends org.apache.commons.mail.Email {
    @java.lang.Override
    public org.apache.commons.mail.Email setMsg(final java.lang.String msg) {
        return null;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public javax.mail.Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public java.lang.String getCharset() {
        return this.charset;
    }

    public java.lang.Object getContentObject() {
        return this.content;
    }

    public javax.mail.internet.MimeMultipart getContentMimeMultipart() {
        return this.emailBody;
    }

    public javax.mail.internet.MimeMultipart getEmailBody() {
        return this.emailBody;
    }

    @java.lang.Override
    public javax.mail.internet.InternetAddress getFromAddress() {
        return this.fromAddress;
    }

    public java.util.Map<java.lang.String, java.lang.String> getHeaders() {
        return this.headers;
    }

    @java.lang.Override
    public java.lang.String getHostName() {
        return this.hostName;
    }

    public javax.mail.internet.MimeMessage getMessage() {
        return this.message;
    }

    public java.lang.String getPopHost() {
        return this.popHost;
    }

    public java.lang.String getPopPassword() {
        return this.popPassword;
    }

    public java.lang.String getPopUsername() {
        return this.popUsername;
    }

    public java.lang.String getContentType() {
        return contentType;
    }

    public boolean isPopBeforeSmtp() {
        return popBeforeSmtp;
    }

    public javax.mail.Session getSession() throws org.apache.commons.mail.EmailException {
        return getMailSession();
    }
}

