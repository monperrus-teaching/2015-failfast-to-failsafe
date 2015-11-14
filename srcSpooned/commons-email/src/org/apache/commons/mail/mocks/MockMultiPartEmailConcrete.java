package org.apache.commons.mail.mocks;


public class MockMultiPartEmailConcrete extends org.apache.commons.mail.MultiPartEmail {
    public java.lang.String getMsg() {
        try {
            return getPrimaryBodyPart().getContent().toString();
        } catch (final java.io.IOException ioE) {
            return null;
        } catch (final javax.mail.MessagingException msgE) {
            return null;
        }
    }

    public void initTest() {
        init();
    }

    @java.lang.Override
    public javax.mail.internet.InternetAddress getFromAddress() {
        return this.fromAddress;
    }
}

