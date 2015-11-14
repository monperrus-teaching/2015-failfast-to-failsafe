package org.apache.commons.mail.mocks;


public class MockSimpleEmail extends org.apache.commons.mail.SimpleEmail {
    public java.lang.String getMsg() {
        return ((java.lang.String)(this.content));
    }

    @java.lang.Override
    public javax.mail.internet.InternetAddress getFromAddress() {
        return this.fromAddress;
    }
}

