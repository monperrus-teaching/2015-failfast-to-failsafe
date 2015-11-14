package org.apache.commons.mail;


@org.junit.runner.RunWith(value = org.powermock.modules.junit4.PowerMockRunner.class)
@org.powermock.core.classloader.annotations.PrepareForTest(value = { org.apache.commons.mail.mocks.MockHtmlEmailConcrete.class })
public class HtmlEmailTest extends org.apache.commons.mail.AbstractEmailTest {
    private org.apache.commons.mail.mocks.MockHtmlEmailConcrete email;

    @org.junit.Before
    public void setUpHtmlEmailTest() {
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
    }

    @org.junit.Test
    public void testGetSetTextMsg() throws org.apache.commons.mail.EmailException {
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setTextMsg(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getTextMsg());
        }
        for (final java.lang.String invalidChar : this.testCharsNotValid) {
            try {
                this.email.setTextMsg(invalidChar);
                org.junit.Assert.fail("Should have thrown an exception");
            } catch (final org.apache.commons.mail.EmailException e) {
                org.junit.Assert.assertTrue(true);
            }
        }
    }

    @org.junit.Test
    public void testGetSetHtmlMsg() throws org.apache.commons.mail.EmailException {
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setHtmlMsg(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getHtmlMsg());
        }
        for (final java.lang.String invalidChar : this.testCharsNotValid) {
            try {
                this.email.setHtmlMsg(invalidChar);
                org.junit.Assert.fail("Should have thrown an exception");
            } catch (final org.apache.commons.mail.EmailException e) {
                org.junit.Assert.assertTrue(true);
            }
        }
    }

    @org.junit.Test
    public void testGetSetMsg() throws org.apache.commons.mail.EmailException {
        for (final java.lang.String validChar : testCharsValid) {
            this.email.setMsg(validChar);
            org.junit.Assert.assertEquals(validChar, this.email.getTextMsg());
            org.junit.Assert.assertTrue(this.email.getHtmlMsg().contains(validChar));
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
    public void testEmbedUrl() throws java.lang.Exception {
        final java.lang.String strEmbed = this.email.embed(new java.net.URL(this.strTestURL), "Test name");
        org.junit.Assert.assertNotNull(strEmbed);
        org.junit.Assert.assertEquals(org.apache.commons.mail.HtmlEmail.CID_LENGTH, strEmbed.length());
        final java.lang.String testCid = this.email.embed(new java.net.URL(this.strTestURL), "Test name");
        org.junit.Assert.assertEquals(strEmbed, testCid);
        final java.lang.String newCid = this.email.embed(new java.net.URL(this.strTestURL), "Test name 2");
        org.junit.Assert.assertFalse(strEmbed.equals(newCid));
        try {
            this.email.embed(createInvalidURL(), "Bad URL");
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
        }
        try {
            this.email.embed(new java.net.URL("http://www.google.com"), "Test name");
            org.junit.Assert.fail("shouldn\'t be able to use an existing name with a different URL!");
        } catch (final org.apache.commons.mail.EmailException e) {
        }
    }

    @org.junit.Test
    public void testEmbedFile() throws java.lang.Exception {
        final java.io.File file = java.io.File.createTempFile("testEmbedFile", "txt");
        file.deleteOnExit();
        final java.lang.String strEmbed = this.email.embed(file);
        org.junit.Assert.assertNotNull(strEmbed);
        org.junit.Assert.assertEquals("generated CID has wrong length", org.apache.commons.mail.HtmlEmail.CID_LENGTH, strEmbed.length());
        final java.lang.String testCid = this.email.embed(file);
        org.junit.Assert.assertEquals("didn\'t get same CID after embedding same file twice", strEmbed, testCid);
        final java.io.File otherFile = java.io.File.createTempFile("testEmbedFile2", "txt");
        otherFile.deleteOnExit();
        final java.lang.String newCid = this.email.embed(otherFile);
        org.junit.Assert.assertFalse("didn\'t get unique CID from embedding new file", strEmbed.equals(newCid));
    }

    @org.junit.Test
    public void testEmbedUrlAndFile() throws java.lang.Exception {
        final java.io.File tmpFile = java.io.File.createTempFile("testfile", "txt");
        tmpFile.deleteOnExit();
        final java.lang.String fileCid = this.email.embed(tmpFile);
        final java.net.URL fileUrl = tmpFile.toURI().toURL();
        final java.lang.String urlCid = this.email.embed(fileUrl, "urlName");
        org.junit.Assert.assertFalse("file and URL cids should be different even for same resource", fileCid.equals(urlCid));
    }

    @org.junit.Test
    public void testEmbedDataSource() throws java.lang.Exception {
        final java.io.File tmpFile = java.io.File.createTempFile("testEmbedDataSource", "txt");
        tmpFile.deleteOnExit();
        final javax.activation.FileDataSource dataSource = new javax.activation.FileDataSource(tmpFile);
        try {
            this.email.embed(dataSource, "");
            org.junit.Assert.fail("embedding with an empty string for a name should fail");
        } catch (final org.apache.commons.mail.EmailException e) {
        }
        final java.lang.String cid = this.email.embed(dataSource, "testname");
        final java.lang.String sameCid = this.email.embed(dataSource, "testname");
        org.junit.Assert.assertEquals("didn\'t get same CID for embedding same datasource twice", cid, sameCid);
        final java.io.File anotherFile = java.io.File.createTempFile("testEmbedDataSource2", "txt");
        anotherFile.deleteOnExit();
        final javax.activation.FileDataSource anotherDS = new javax.activation.FileDataSource(anotherFile);
        try {
            this.email.embed(anotherDS, "testname");
        } catch (final org.apache.commons.mail.EmailException e) {
        }
    }

    @org.junit.Test
    public void testSend() throws java.io.IOException, org.apache.commons.mail.EmailException {
        final org.apache.commons.mail.EmailAttachment attachment = new org.apache.commons.mail.EmailAttachment();
        java.io.File testFile = null;
        testFile = java.io.File.createTempFile("commons-email-testfile", ".txt");
        testFile.deleteOnExit();
        getMailServer();
        java.lang.String strSubject = "Test HTML Send #1 Subject (w charset)";
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        attachment.setName("Test Attachment");
        attachment.setDescription("Test Attachment Desc");
        attachment.setPath(testFile.getAbsolutePath());
        this.email.attach(attachment);
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
        validateSend(this.fakeMailServer, strSubject, attachment.getName(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        strSubject = "Test HTML Send #1 Subject (wo charset)";
        this.email.setSubject(strSubject);
        this.email.setTextMsg("Test message");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getTextMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
    }

    @org.junit.Test
    public void testSend2() throws java.lang.Exception {
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        java.lang.String strSubject = "Test HTML Send #2 Subject (wo charset)";
        this.email.setSubject(strSubject);
        this.email.setMsg("Test txt msg");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getTextMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
        validateSend(this.fakeMailServer, strSubject, this.email.getHtmlMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setFrom(this.strTestMailFrom);
        this.email.setSmtpPort(getMailServerPort());
        this.email.addTo(this.strTestMailTo);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        strSubject = "Test HTML Send #2 Subject (w charset)";
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        this.email.setMsg("Test txt msg");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getTextMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
        validateSend(this.fakeMailServer, strSubject, this.email.getHtmlMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
    }

    @org.junit.Test
    @org.junit.Ignore
    public void testSendWithDefaultCharset() throws java.lang.Exception {
        java.lang.System.setProperty(org.apache.commons.mail.EmailConstants.MAIL_MIME_CHARSET, "iso-8859-15");
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        final java.lang.String strSubject = "Test HTML Send Subject (w default charset)";
        this.email.setSubject(strSubject);
        this.email.setMsg("Test txt msg ä");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, "charset=iso-8859-15", this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
        java.lang.System.clearProperty(org.apache.commons.mail.EmailConstants.MAIL_MIME_CHARSET);
    }

    @org.junit.Test
    public void testAddZipUrl() throws java.lang.Exception {
        final java.lang.String htmlMsg = "Please click on the following link: <br><br>" + ("<a href=\"http://paradisedelivery.homeip.net/delivery/?file=3DTZC268X93337.zip\">" + ("http://paradisedelivery.homeip.net/delivery/?file=3DTZC268X93337.zip" + "</a><br><br>Customer satisfaction is very important for us."));
        getMailServer();
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        final java.lang.String strSubject = "A dot (\".\") is appended to some ULRs of a HTML mail.";
        this.email.setSubject(strSubject);
        this.email.setHtmlMsg(htmlMsg);
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getHtmlMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), false);
        org.junit.Assert.assertTrue(this.email.getHtmlMsg().contains("3DTZC268X93337.zip"));
        org.junit.Assert.assertFalse(this.email.getHtmlMsg().contains("3DTZC268X93337..zip"));
    }

    @org.junit.Test
    public void testCallingBuildMimeMessageBeforeSent() throws java.lang.Exception {
        final java.lang.String htmlMsg = "<b>Hello World</b>";
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        if (((this.strTestUser) != null) && ((this.strTestPasswd) != null)) {
            this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        } 
        final java.lang.String strSubject = "testCallingBuildMimeMessageBeforeSent";
        this.email.setSubject(strSubject);
        this.email.setHtmlMsg(htmlMsg);
        this.email.buildMimeMessage();
        try {
            this.email.send();
        } catch (final java.lang.IllegalStateException e) {
            return ;
        }
        org.junit.Assert.fail("Expecting an exception when calling buildMimeMessage() before send() ...");
    }

    @org.junit.Test
    public void testSendWithPlainTextButNoHtmlContent() throws java.io.IOException, org.apache.commons.mail.EmailException {
        getMailServer();
        final java.lang.String strSubject = "testSendWithPlainTextButNoHtmlContent";
        this.email = new org.apache.commons.mail.mocks.MockHtmlEmailConcrete();
        this.email.setHostName(this.strTestMailServer);
        this.email.setSmtpPort(getMailServerPort());
        this.email.setFrom(this.strTestMailFrom);
        this.email.addTo(this.strTestMailTo);
        this.email.setAuthentication(this.strTestUser, this.strTestPasswd);
        this.email.setCharset(org.apache.commons.mail.EmailConstants.ISO_8859_1);
        this.email.setSubject(strSubject);
        this.email.setMsg("This is a plain text content : <b><&npsb;></html></b>");
        this.email.send();
        this.fakeMailServer.stop();
        validateSend(this.fakeMailServer, strSubject, this.email.getTextMsg(), this.email.getFromAddress(), this.email.getToAddresses(), this.email.getCcAddresses(), this.email.getBccAddresses(), true);
    }

    @org.junit.Test
    public void testEmbedFileWithCID() throws java.lang.Exception {
        final java.io.File file = java.io.File.createTempFile("testEmbedFile", "txt");
        file.deleteOnExit();
        final java.lang.String testCid = "Test CID";
        final java.lang.String encodedCid = org.apache.commons.mail.EmailUtils.encodeUrl(testCid);
        final java.lang.String strEmbed = this.email.embed(file, testCid);
        org.junit.Assert.assertNotNull(strEmbed);
        org.junit.Assert.assertEquals("didn\'t get same CID when embedding with a specified CID", encodedCid, strEmbed);
        final java.lang.String returnedCid = this.email.embed(file);
        org.junit.Assert.assertEquals("didn\'t get same CID after embedding same file twice", encodedCid, returnedCid);
    }

    @org.junit.Test
    public void testHtmlMailMimeLayout() throws java.lang.Exception {
        assertCorrectContentType("contentTypeTest.gif", "image/gif");
        assertCorrectContentType("contentTypeTest.jpg", "image/jpeg");
        assertCorrectContentType("contentTypeTest.png", "image/png");
    }

    private void assertCorrectContentType(final java.lang.String picture, final java.lang.String contentType) throws java.lang.Exception {
        final org.apache.commons.mail.HtmlEmail htmlEmail = createDefaultHtmlEmail();
        final java.lang.String cid = htmlEmail.embed(new java.io.File(("./src/test/resources/images/" + picture)), "Apache Logo");
        final java.lang.String htmlMsg = ("<html><img src=\"cid:" + cid) + "\"><html>";
        htmlEmail.setHtmlMsg(htmlMsg);
        htmlEmail.buildMimeMessage();
        final javax.mail.internet.MimeMessage mm = htmlEmail.getMimeMessage();
        mm.saveChanges();
        final org.apache.commons.mail.util.MimeMessageParser mmp = new org.apache.commons.mail.util.MimeMessageParser(mm);
        mmp.parse();
        final java.util.List<?> attachments = mmp.getAttachmentList();
        org.junit.Assert.assertEquals("Attachment size", 1, attachments.size());
        final javax.activation.DataSource ds = ((javax.activation.DataSource)(attachments.get(0)));
        org.junit.Assert.assertEquals("Content type", contentType, ds.getContentType());
    }

    private org.apache.commons.mail.HtmlEmail createDefaultHtmlEmail() throws org.apache.commons.mail.EmailException {
        final org.apache.commons.mail.HtmlEmail htmlEmail = new org.apache.commons.mail.HtmlEmail();
        htmlEmail.setHostName(this.strTestMailServer);
        htmlEmail.setSmtpPort(getMailServerPort());
        htmlEmail.setFrom("a@b.com");
        htmlEmail.addTo("c@d.com");
        return htmlEmail;
    }
}

