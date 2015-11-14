package org.apache.commons.mail;


public abstract class AbstractEmailTest {
    public static final int BODY_END_PAD = 3;

    public static final int BODY_START_PAD = 2;

    private static final java.lang.String LINE_SEPARATOR = "\r\n";

    private static int mailServerPort = 2500;

    protected org.subethamail.wiser.Wiser fakeMailServer;

    protected java.lang.String strTestMailServer = "localhost";

    protected java.lang.String strTestMailFrom = "test_from@apache.org";

    protected java.lang.String strTestMailTo = "test_to@apache.org";

    protected java.lang.String strTestUser = "user";

    protected java.lang.String strTestPasswd = "password";

    protected java.lang.String strTestURL = org.apache.commons.mail.settings.EmailConfiguration.TEST_URL;

    protected java.lang.String[] testCharsValid = new java.lang.String[]{ " " , "a" , "A" , "여" , "0123456789" , "012345678901234567890" , "\n" };

    protected java.lang.String[] testCharsNotValid = new java.lang.String[]{ "" , null };

    private java.io.File emailOutputDir;

    private static int fileNameCounter;

    @org.junit.Before
    public void setUpAbstractEmailTest() {
        emailOutputDir = new java.io.File("target/test-emails");
        if (!(emailOutputDir.exists())) {
            emailOutputDir.mkdirs();
        } 
    }

    @org.junit.After
    public void tearDownEmailTest() {
        if (((this.fakeMailServer) != null) && (!(isMailServerStopped(fakeMailServer)))) {
            this.fakeMailServer.stop();
            org.junit.Assert.assertTrue("Mail server didn\'t stop", isMailServerStopped(fakeMailServer));
        } 
        this.fakeMailServer = null;
    }

    protected int getMailServerPort() {
        return org.apache.commons.mail.AbstractEmailTest.mailServerPort;
    }

    protected void saveEmailToFile(final org.subethamail.wiser.WiserMessage email) throws java.io.IOException, javax.mail.MessagingException {
        final int currCounter = ((org.apache.commons.mail.AbstractEmailTest.fileNameCounter)++) % 10;
        final java.lang.String emailFileName = ((("email" + (new java.util.Date().getTime())) + "-") + currCounter) + ".eml";
        final java.io.File emailFile = new java.io.File(emailOutputDir , emailFileName);
        org.apache.commons.mail.EmailUtils.writeMimeMessage(emailFile, email.getMimeMessage());
    }

    public java.lang.String getMessageAsString(final int intMsgNo) {
        final java.util.List<?> receivedMessages = fakeMailServer.getMessages();
        org.junit.Assert.assertTrue("mail server didn\'t get enough messages", ((receivedMessages.size()) >= intMsgNo));
        final org.subethamail.wiser.WiserMessage emailMessage = ((org.subethamail.wiser.WiserMessage)(receivedMessages.get(intMsgNo)));
        if (emailMessage != null) {
            try {
                return serializeEmailMessage(emailMessage);
            } catch (final java.lang.Exception e) {
            }
        } 
        org.junit.Assert.fail("Message not found");
        return "";
    }

    public void getMailServer() {
        if (((this.fakeMailServer) == null) || (isMailServerStopped(fakeMailServer))) {
            (org.apache.commons.mail.AbstractEmailTest.mailServerPort)++;
            this.fakeMailServer = new org.subethamail.wiser.Wiser();
            this.fakeMailServer.setPort(getMailServerPort());
            this.fakeMailServer.start();
            org.junit.Assert.assertFalse("fake mail server didn\'t start", isMailServerStopped(fakeMailServer));
            final java.util.Date dtStartWait = new java.util.Date();
            while (isMailServerStopped(fakeMailServer)) {
                if (((this.fakeMailServer) != null) && (!(isMailServerStopped(fakeMailServer)))) {
                    break;
                } 
                if (((dtStartWait.getTime()) + (org.apache.commons.mail.settings.EmailConfiguration.TIME_OUT)) <= (new java.util.Date().getTime())) {
                    org.junit.Assert.fail("Mail server failed to start");
                } 
            }
        } 
    }

    protected org.subethamail.wiser.WiserMessage validateSend(final org.subethamail.wiser.Wiser mailServer, final java.lang.String strSubject, final javax.mail.internet.InternetAddress fromAdd, final java.util.List<javax.mail.internet.InternetAddress> toAdd, final java.util.List<javax.mail.internet.InternetAddress> ccAdd, final java.util.List<javax.mail.internet.InternetAddress> bccAdd, final boolean boolSaveToFile) throws java.io.IOException {
        org.junit.Assert.assertTrue("mail server doesn\'t contain expected message", ((mailServer.getMessages().size()) == 1));
        final org.subethamail.wiser.WiserMessage emailMessage = mailServer.getMessages().get(0);
        if (boolSaveToFile) {
            try {
                saveEmailToFile(emailMessage);
            } catch (final javax.mail.MessagingException me) {
                final java.lang.IllegalStateException ise = new java.lang.IllegalStateException("caught MessagingException during saving the email");
                ise.initCause(me);
                throw ise;
            }
        } 
        try {
            final javax.mail.internet.MimeMessage mimeMessage = emailMessage.getMimeMessage();
            org.junit.Assert.assertEquals("got wrong subject from mail", strSubject, mimeMessage.getHeader("Subject", null));
            org.junit.Assert.assertEquals("got wrong From: address from mail", fromAdd.toString(), mimeMessage.getHeader("From", null));
            org.junit.Assert.assertTrue("got wrong To: address from mail", toAdd.toString().contains(mimeMessage.getHeader("To", null)));
            if ((ccAdd.size()) > 0) {
                org.junit.Assert.assertTrue("got wrong Cc: address from mail", ccAdd.toString().contains(mimeMessage.getHeader("Cc", null)));
            } 
            if ((bccAdd.size()) > 0) {
                org.junit.Assert.assertTrue("got wrong Bcc: address from mail", bccAdd.toString().contains(mimeMessage.getHeader("Bcc", null)));
            } 
        } catch (final javax.mail.MessagingException me) {
            final java.lang.IllegalStateException ise = new java.lang.IllegalStateException("caught MessagingException in validateSend()");
            ise.initCause(me);
            throw ise;
        }
        return emailMessage;
    }

    protected void validateSend(final org.subethamail.wiser.Wiser mailServer, final java.lang.String strSubject, final javax.mail.Multipart content, final javax.mail.internet.InternetAddress fromAdd, final java.util.List<javax.mail.internet.InternetAddress> toAdd, final java.util.List<javax.mail.internet.InternetAddress> ccAdd, final java.util.List<javax.mail.internet.InternetAddress> bccAdd, final boolean boolSaveToFile) throws java.io.IOException {
        final org.subethamail.wiser.WiserMessage emailMessage = validateSend(mailServer, strSubject, fromAdd, toAdd, ccAdd, bccAdd, boolSaveToFile);
        final java.lang.String strSentContent = content.getContentType();
        final java.lang.String emailMessageBody = getMessageBody(emailMessage);
        final java.lang.String strMessageBody = emailMessageBody.substring(BODY_START_PAD, ((emailMessageBody.length()) - (BODY_END_PAD)));
        org.junit.Assert.assertTrue("didn\'t find expected content type in message body", strMessageBody.contains(strSentContent));
    }

    protected void validateSend(final org.subethamail.wiser.Wiser mailServer, final java.lang.String strSubject, final java.lang.String strMessage, final javax.mail.internet.InternetAddress fromAdd, final java.util.List<javax.mail.internet.InternetAddress> toAdd, final java.util.List<javax.mail.internet.InternetAddress> ccAdd, final java.util.List<javax.mail.internet.InternetAddress> bccAdd, final boolean boolSaveToFile) throws java.io.IOException {
        final org.subethamail.wiser.WiserMessage emailMessage = validateSend(mailServer, strSubject, fromAdd, toAdd, ccAdd, bccAdd, true);
        org.junit.Assert.assertTrue("didn\'t find expected message content in message body", getMessageBody(emailMessage).contains(strMessage));
    }

    private java.lang.String serializeEmailMessage(final org.subethamail.wiser.WiserMessage wiserMessage) throws java.io.IOException, javax.mail.MessagingException {
        if (wiserMessage == null) {
            return "";
        } 
        final java.lang.StringBuffer serializedEmail = new java.lang.StringBuffer();
        final javax.mail.internet.MimeMessage message = wiserMessage.getMimeMessage();
        for (final java.util.Enumeration<?> headers = message.getAllHeaders() ; headers.hasMoreElements() ; ) {
            final javax.mail.Header header = ((javax.mail.Header)(headers.nextElement()));
            serializedEmail.append(header.getName());
            serializedEmail.append(": ");
            serializedEmail.append(header.getValue());
            serializedEmail.append(LINE_SEPARATOR);
        }
        final byte[] messageBody = getMessageBodyBytes(message);
        serializedEmail.append(LINE_SEPARATOR);
        serializedEmail.append(messageBody);
        serializedEmail.append(LINE_SEPARATOR);
        return serializedEmail.toString();
    }

    private java.lang.String getMessageBody(final org.subethamail.wiser.WiserMessage wiserMessage) throws java.io.IOException {
        if (wiserMessage == null) {
            return "";
        } 
        byte[] messageBody = null;
        try {
            final javax.mail.internet.MimeMessage message = wiserMessage.getMimeMessage();
            messageBody = getMessageBodyBytes(message);
        } catch (final javax.mail.MessagingException me) {
            final java.lang.IllegalStateException ise = new java.lang.IllegalStateException("couldn\'t process MimeMessage from WiserMessage in getMessageBody()");
            ise.initCause(me);
            throw ise;
        }
        return messageBody != null ? new java.lang.String(messageBody).intern() : "";
    }

    private byte[] getMessageBodyBytes(final javax.mail.internet.MimeMessage mimeMessage) throws java.io.IOException, javax.mail.MessagingException {
        final javax.activation.DataHandler dataHandler = mimeMessage.getDataHandler();
        final java.io.ByteArrayOutputStream byteArrayOutStream = new java.io.ByteArrayOutputStream();
        final java.io.BufferedOutputStream buffOs = new java.io.BufferedOutputStream(byteArrayOutStream);
        dataHandler.writeTo(buffOs);
        buffOs.flush();
        return byteArrayOutStream.toByteArray();
    }

    protected boolean isMailServerStopped(final org.subethamail.wiser.Wiser fakeMailServer) {
        return !(fakeMailServer.getServer().isRunning());
    }

    protected java.net.URL createInvalidURL() throws java.lang.Exception {
        final java.net.URL url = org.powermock.api.easymock.PowerMock.createMock(java.net.URL.class);
        org.easymock.EasyMock.expect(url.openStream()).andThrow(new java.io.IOException());
        org.powermock.api.easymock.PowerMock.replay(url);
        return url;
    }
}

