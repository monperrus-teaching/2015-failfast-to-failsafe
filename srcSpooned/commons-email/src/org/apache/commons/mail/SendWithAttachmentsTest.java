package org.apache.commons.mail;


public class SendWithAttachmentsTest extends org.apache.commons.mail.AbstractEmailTest {
    private org.apache.commons.mail.mocks.MockHtmlEmailConcrete email;

    @org.junit.Before
    public void setUpSendWithAttachmentsTest() {
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
    }

    @org.junit.Test
    public void testSendNoAttachments() throws java.io.IOException, org.apache.commons.mail.EmailException {
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send #1 Subject (w charset)";
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        final java.net.URL url = new java.net.URL(org.apache.commons.mail.settings.EmailConfiguration.TEST_URL);
        final java.lang.String cid = this.email.embed(url, "Apache Logo");
        final java.lang.String strHtmlMsg = ("<html>The Apache logo - <img src=\"cid:" + cid) + "\"><html>";
        this.email.setHtmlMsg(strHtmlMsg);
        this.email.setTextMsg("Your email client does not support HTML emails");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getTextMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
        validateSend(this.fakeMailServer, strSubject, this.email.getHtmlMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
    }

    @org.junit.Test
    public void testSendWAttachments() throws java.io.IOException, org.apache.commons.mail.EmailException {
        final org.apache.commons.mail.EmailAttachment attachment = new org.apache.commons.mail.EmailAttachment();
        java.io.File testFile = null;
        testFile = java.io.File.createTempFile("commons-email-testfile", ".txt");
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send #1 Subject (w charset)";
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        attachment.setName("Test Attachment - a>ä, o>ö, u>ü, au>äu");
        attachment.setDescription("Test Attachment Desc");
        attachment.setPath(testFile.getAbsolutePath());
        this.email.attach(attachment);
        this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        final java.lang.String strHtmlMsg = "<html>Test Message<html>";
        this.email.setHtmlMsg(strHtmlMsg);
        this.email.setTextMsg("Your email client does not support HTML emails");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getTextMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
        validateSend(this.fakeMailServer, strSubject, this.email.getHtmlMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
        validateSend(this.fakeMailServer, strSubject, javax.mail.internet.MimeUtility.encodeText(attachment.getName()), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
    }
}

