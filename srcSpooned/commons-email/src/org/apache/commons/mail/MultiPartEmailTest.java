package org.apache.commons.mail;


@org.junit.runner.RunWith(value = org.powermock.modules.junit4.PowerMockRunner.class)
@org.powermock.core.classloader.annotations.PrepareForTest(value = { org.apache.commons.mail.mocks.MockMultiPartEmailConcrete.class , javax.activation.URLDataSource.class })
public class MultiPartEmailTest extends org.apache.commons.mail.AbstractEmailTest {
    private org.apache.commons.mail.mocks.MockMultiPartEmailConcrete email;

    private java.io.File testFile;

    @org.junit.Before
    public void setUpMultiPartEmailTest() throws java.lang.Exception {
        this.email = new org.apache.commons.mail.mocks.MockMultiPartEmailConcrete();
        testFile = java.io.File.createTempFile("testfile", ".txt");
    }

    @org.junit.Test
    public void testSetMsg() throws org.apache.commons.mail.EmailException {
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setMsg(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getMsg());
        }
        this.email.setCharset(org.apache.commons.mail.EmailConstants.US_ASCII);
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setMsg(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getMsg());
        }
        for (final java.lang.String invalidChar : testCharsNotValid) {
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
        final java.lang.String strSubject = "Test Multipart Send Subject";
        final org.apache.commons.mail.EmailAttachment attachment = new org.apache.commons.mail.EmailAttachment();
        attachment.setPath(testFile.getAbsolutePath());
        attachment.setDisposition(org.apache.commons.mail.EmailAttachment.ATTACHMENT);
        attachment.setName("Test_Attachment");
        attachment.setDescription("Test Attachment Desc");
        final org.apache.commons.mail.mocks.MockMultiPartEmailConcrete testEmail = new org.apache.commons.mail.mocks.MockMultiPartEmailConcrete();
        testEmail.setHostName(this.strTestMailServer);
        testEmail.setSmtpPort(getMailServerPort());
        testEmail.setFrom(this.strTestMailFrom);
        testEmail.addTo(this.strTestMailTo);
        testEmail.attach(attachment);
        testEmail.setSubType("subType");
        if ((org.apache.commons.mail.EmailUtils.isNotEmpty(this.strTestUser)) && (org.apache.commons.mail.EmailUtils.isNotEmpty(this.strTestPasswd))) {
            testEmail.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        testEmail.setSubject(strSubject);
        testEmail.setMsg("Test Message");
        final java.util.Map<java.lang.String, java.lang.String> ht = new java.util.HashMap<java.lang.String, java.lang.String>();
        ht.put("X-Priority", "2");
        ht.put("Disposition-Notification-To", this.strTestMailFrom);
        ht.put("X-Mailer", "Sendmail");
        testEmail.setHeaders(ht);
        testEmail.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, testEmail.getMsg(), testEmail.getFromAddress(), testEmail.getToAddresses(), testEmail.getCcAddresses(), testEmail.getBccAddresses(), true);
        validateSend(this.fakeMailServer, strSubject, attachment.getName(), testEmail.getFromAddress(), testEmail.getToAddresses(), testEmail.getCcAddresses(), testEmail.getBccAddresses(), false);
        try {
            getMailServer();
            this.email.send();
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            this.fakeMailServer.stop();
        }
    }

    @org.junit.Test
    public void testAttach() throws java.lang.Exception {
        org.apache.commons.mail.EmailAttachment attachment;
        attachment = new org.apache.commons.mail.EmailAttachment();
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setPath(testFile.getAbsolutePath());
        this.email.attach(attachment);
        org.junit.Assert.assertTrue(this.email.isBoolHasAttachments());
        attachment = new org.apache.commons.mail.EmailAttachment();
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setURL(new java.net.URL(this.strTestURL));
        this.email.attach(attachment);
        this.email.attach(testFile);
        org.junit.Assert.assertTrue(this.email.isBoolHasAttachments());
        try {
            this.email.attach(((org.apache.commons.mail.EmailAttachment)(null)));
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(true);
        }
        attachment = new org.apache.commons.mail.EmailAttachment();
        try {
            attachment.setURL(createInvalidURL());
            this.email.attach(attachment);
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(true);
        }
        attachment = new org.apache.commons.mail.EmailAttachment();
        try {
            attachment.setPath("");
            this.email.attach(attachment);
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(true);
        }
    }

    @org.junit.Test
    public void testAttach2() throws java.net.MalformedURLException, org.apache.commons.mail.EmailException {
        this.email.attach(new java.net.URL(this.strTestURL), "Test Attachment", "Test Attachment Desc");
        this.email.attach(new java.net.URL(this.strTestURL), null, "Test Attachment Desc");
    }

    @org.junit.Test
    public void testAttach3() throws java.lang.Exception {
        this.email.attach(new javax.activation.URLDataSource(new java.net.URL(this.strTestURL)), "Test Attachment", "Test Attachment Desc");
        try {
            final javax.activation.URLDataSource urlDs = null;
            this.email.attach(urlDs, "Test Attachment", "Test Attachment Desc");
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(true);
        }
        try {
            final javax.activation.URLDataSource urlDs = new javax.activation.URLDataSource(createInvalidURL());
            this.email.attach(urlDs, "Test Attachment", "Test Attachment Desc");
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(true);
        }
    }

    @org.junit.Test
    public void testAttachFileLocking() throws java.lang.Exception {
        final java.io.File tmpFile = java.io.File.createTempFile("attachment", ".eml");
        this.email.attach(new javax.activation.FileDataSource(tmpFile), "Test Attachment", "Test Attachment Desc");
        org.junit.Assert.assertTrue(tmpFile.delete());
    }

    @org.junit.Test
    public void testAddPart() throws java.lang.Exception {
        this.email = new org.apache.commons.mail.mocks.MockMultiPartEmailConcrete();
        final java.lang.String strMessage = "hello";
        final java.lang.String strContentType = "text/plain";
        this.email.addPart(strMessage, strContentType);
        org.junit.Assert.assertEquals(strContentType, this.email.getContainer().getBodyPart(0).getContentType());
        org.junit.Assert.assertEquals(strMessage, this.email.getContainer().getBodyPart(0).getDataHandler().getContent());
    }

    @org.junit.Test
    public void testAddPart2() throws java.lang.Exception {
        this.email = new org.apache.commons.mail.mocks.MockMultiPartEmailConcrete();
        final java.lang.String strSubtype = "subtype/abc123";
        this.email.addPart(new javax.mail.internet.MimeMultipart(strSubtype));
        org.junit.Assert.assertTrue(this.email.getContainer().getBodyPart(0).getDataHandler().getContentType().contains(strSubtype));
    }

    @org.junit.Test
    public void testGetContainer() {
        org.junit.Assert.assertTrue(true);
    }

    @org.junit.Test
    public void testInit() {
        try {
            this.email.init();
            this.email.init();
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final java.lang.IllegalStateException e) {
            org.junit.Assert.assertTrue(true);
        }
    }

    @org.junit.Test
    public void testGetSetSubType() {
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setSubType(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getSubType());
        }
    }
}

