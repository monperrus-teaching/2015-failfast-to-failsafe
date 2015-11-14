package org.apache.commons.mail;


public class SimpleEmailTest extends org.apache.commons.mail.AbstractEmailTest {
    private org.apache.commons.mail.mocks.MockSimpleEmail email;

    @org.junit.Before
    public void setUpSimpleEmailTest() {
        this.email = new org.apache.commons.mail.mocks.MockSimpleEmail();
    }

    @org.junit.Test
    public void testGetSetMsg() throws org.apache.commons.mail.EmailException {
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setMsg(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getMsg());
        }
        for (final java.lang.String invalidChar : this.testCharsNotValid) {
            try {
                this.email.setMsg(invalidChar);
                org.junit.Assert.fail("Should have thrown an exception");
            } catch (final org.apache.commons.mail.EmailException e) {
                org.junit.Assert.assertTrue(true);
            }
        }
    }

    @org.junit.Test
    public void testSend() throws java.io.IOException, org.apache.commons.mail.EmailException {
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockSimpleEmail();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        final java.lang.String strSubject = "Test Msg Subject";
        final java.lang.String strMessage = "Test Msg Body";
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        this.email.setMsg(strMessage);
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
    }

    @org.junit.Test
    @org.junit.Ignore
    public void testDefaultMimeCharset() throws java.io.IOException, org.apache.commons.mail.EmailException {
        java.lang.System.setProperty(org.apache.commons.mail.EmailConstants.MAIL_MIME_CHARSET, "utf-8");
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockSimpleEmail();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        final java.lang.String strSubject = "Test Msg Subject";
        final java.lang.String strMessage = "Test Msg Body ä";
        this.email.setSubject(strSubject);
        this.email.setMsg(strMessage);
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getMsg().substring(0, 13), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
        final java.lang.String message = getMessageAsString(0);
        org.junit.Assert.assertTrue(message.toLowerCase().contains("content-type: text/plain; charset=utf-8"));
        java.lang.System.clearProperty(org.apache.commons.mail.EmailConstants.MAIL_MIME_CHARSET);
    }
}

