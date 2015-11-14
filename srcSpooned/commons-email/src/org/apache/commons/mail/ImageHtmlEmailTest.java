package org.apache.commons.mail;


public class ImageHtmlEmailTest extends org.apache.commons.mail.HtmlEmailTest {
    private static final boolean TEST_IS_LENIENT = true;

    private static final java.net.URL TEST_IMAGE_URL = org.apache.commons.mail.ImageHtmlEmailTest.class.getResource("/images/asf_logo_wide.gif");

    private static final java.io.File TEST_IMAGE_DIR = new java.io.File(TEST_IMAGE_URL.getPath()).getParentFile();

    private static final java.net.URL TEST_HTML_URL = org.apache.commons.mail.ImageHtmlEmailTest.class.getResource("/attachments/download_email.cgi.html");

    private static final java.net.URL TEST2_HTML_URL = org.apache.commons.mail.ImageHtmlEmailTest.class.getResource("/attachments/classpathtest.html");

    private org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete email;

    @org.junit.Before
    public void setupImageHtmlEmailTest() {
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
    }

    @org.junit.Test
    public void testSendHtml() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(TEST_IMAGE_DIR.toURI().toURL() , TEST_IS_LENIENT));
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        final java.lang.String html = loadUrlContent(TEST_HTML_URL);
        email.setHtmlMsg(html);
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
        fakeMailServer.stop();
        org.junit.Assert.assertEquals(1, fakeMailServer.getMessages().size());
        final javax.mail.internet.MimeMessage mimeMessage = fakeMailServer.getMessages().get(0).getMimeMessage();
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(mimeMessage).parse();
        org.junit.Assert.assertTrue(mimeMessageParser.getHtmlContent().contains("\"cid:"));
        org.junit.Assert.assertTrue(((mimeMessageParser.getAttachmentList().size()) == 3));
    }

    @org.junit.Test
    public void testSendEmptyHTML() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        final org.apache.commons.mail.ImageHtmlEmail email = new org.apache.commons.mail.ImageHtmlEmail();
        try {
            email.setHtmlMsg(null);
            org.junit.Assert.fail("Should fail here!");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(e.getMessage(), e.getMessage().contains("Invalid message supplied"));
        }
    }

    @org.junit.Test
    public void testSendEmptyHTML2() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        final org.apache.commons.mail.ImageHtmlEmail email = new org.apache.commons.mail.ImageHtmlEmail();
        try {
            email.setHtmlMsg("");
            org.junit.Assert.fail("Should fail here!");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(e.getMessage(), e.getMessage().contains("Invalid message supplied"));
        }
    }

    @org.junit.Test
    public void testSendHtmlUrl() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default with URL";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(TEST_IMAGE_DIR.toURI().toURL() , TEST_IS_LENIENT));
        email.setHtmlMsg("<html><body><img src=\"http://www.apache.org/images/feather.gif\"/></body></html>");
        email.send();
        fakeMailServer.stop();
        validateSend(fakeMailServer, strSubject, email.getHtmlMsg(), email.getFromAddress(), email.getToAddresses(), email.getCcAddresses(), email.getBccAddresses(), true);
    }

    @org.junit.Test
    public void testSendHTMLAbsoluteLocalFile() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default with absolute local path";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(TEST_IMAGE_DIR.toURI().toURL() , TEST_IS_LENIENT));
        final java.io.File file = java.io.File.createTempFile("emailtest", ".tst");
        org.apache.commons.io.FileUtils.writeStringToFile(file, "just some silly data that we won\'t be able to display anyway");
        email.setHtmlMsg((("<html><body><img src=\"" + (file.getAbsolutePath())) + "\"/></body></html>"));
        email.send();
        fakeMailServer.stop();
        validateSend(fakeMailServer, strSubject, email.getHtmlMsg(), email.getFromAddress(), email.getToAddresses(), email.getCcAddresses(), email.getBccAddresses(), true);
    }

    @org.junit.Test
    public void testSendHTMLClassPathFile() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/" , TEST_IS_LENIENT));
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        final java.lang.String html = loadUrlContent(TEST2_HTML_URL);
        email.setHtmlMsg(html);
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
        fakeMailServer.stop();
        org.junit.Assert.assertEquals(1, fakeMailServer.getMessages().size());
        final javax.mail.internet.MimeMessage mimeMessage = fakeMailServer.getMessages().get(0).getMimeMessage();
        org.apache.commons.mail.util.MimeMessageUtils.writeMimeMessage(mimeMessage, new java.io.File("./target/test-emails/testSendHTMLClassPathFile.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(mimeMessage).parse();
        org.junit.Assert.assertTrue(mimeMessageParser.getHtmlContent().contains("\"cid:"));
        org.junit.Assert.assertTrue(((mimeMessageParser.getAttachmentList().size()) == 1));
    }

    @org.junit.Test
    public void testSendClassPathFileWithNullName() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        email.setDataSourceResolver(new org.apache.commons.mail.ImageHtmlEmailTest.MockDataSourceClassPathResolver("/" , TEST_IS_LENIENT));
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        final java.lang.String html = loadUrlContent(TEST2_HTML_URL);
        email.setHtmlMsg(html);
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
        fakeMailServer.stop();
        org.junit.Assert.assertEquals(1, fakeMailServer.getMessages().size());
        final javax.mail.internet.MimeMessage mimeMessage = fakeMailServer.getMessages().get(0).getMimeMessage();
        org.apache.commons.mail.util.MimeMessageUtils.writeMimeMessage(mimeMessage, new java.io.File("./target/test-emails/testSendClassPathFileWithNullName.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(mimeMessage).parse();
        org.junit.Assert.assertTrue(mimeMessageParser.getHtmlContent().contains("\"cid:"));
        org.junit.Assert.assertTrue(((mimeMessageParser.getAttachmentList().size()) == 1));
    }

    @org.junit.Test
    public void testSendHTMLAutoResolveFile() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        final org.apache.commons.mail.DataSourceResolver[] dataSourceResolvers = new org.apache.commons.mail.DataSourceResolver[2];
        dataSourceResolvers[0] = new org.apache.commons.mail.resolver.DataSourceUrlResolver(new java.net.URL("http://foo") , true);
        dataSourceResolvers[1] = new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/" , true);
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceCompositeResolver(dataSourceResolvers));
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        final java.lang.String html = loadUrlContent(TEST2_HTML_URL);
        email.setHtmlMsg(html);
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
        fakeMailServer.stop();
        org.junit.Assert.assertEquals(1, fakeMailServer.getMessages().size());
        final javax.mail.internet.MimeMessage mimeMessage = fakeMailServer.getMessages().get(0).getMimeMessage();
        org.apache.commons.mail.util.MimeMessageUtils.writeMimeMessage(mimeMessage, new java.io.File("./target/test-emails/testSendHTMLAutoFile.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(mimeMessage).parse();
        org.junit.Assert.assertTrue(mimeMessageParser.getHtmlContent().contains("\"cid:"));
        org.junit.Assert.assertTrue(((mimeMessageParser.getAttachmentList().size()) == 1));
    }

    @org.junit.Test
    public void testSendHTMLAutoResolveMultipleFiles() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/" , true);
        email.setDataSourceResolver(dataSourceResolver);
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        final java.lang.String html = "<p>First image  <img src=\"images/contentTypeTest.gif\"/></p>" + ("<p>Second image <img src=\"images/contentTypeTest.jpg\"/></p>" + "<p>Third image  <img src=\"images/contentTypeTest.png\"/></p>");
        email.setHtmlMsg(html);
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
        fakeMailServer.stop();
        org.junit.Assert.assertEquals(1, fakeMailServer.getMessages().size());
        final javax.mail.internet.MimeMessage mimeMessage = fakeMailServer.getMessages().get(0).getMimeMessage();
        org.apache.commons.mail.util.MimeMessageUtils.writeMimeMessage(mimeMessage, new java.io.File("./target/test-emails/testSendHTMLAutoMultipleFiles.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(mimeMessage).parse();
        org.junit.Assert.assertTrue(mimeMessageParser.getHtmlContent().contains("\"cid:"));
        org.junit.Assert.assertTrue(((mimeMessageParser.getAttachmentList().size()) == 3));
    }

    @org.junit.Test
    public void testRegex() {
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(org.apache.commons.mail.ImageHtmlEmail.REGEX_IMG_SRC);
        java.util.regex.Matcher matcher = pattern.matcher("<html><body><img src=\"h\"/></body></html>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("h", matcher.group(2));
        matcher = pattern.matcher("<html><body><img id=\"laskdasdkj\" src=\"h\"/></body></html>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("h", matcher.group(2));
        matcher = pattern.matcher("<html><body><IMG id=\"laskdasdkj\" SRC=\"h\"/></body></html>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("h", matcher.group(2));
        matcher = pattern.matcher("<html><body><img id=\"laskdasdkj\" src=\"http://dstadler1.org/\"/><img id=\"laskdasdkj\" src=\"http://dstadler2.org/\"/></body></html>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("http://dstadler1.org/", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("http://dstadler2.org/", matcher.group(2));
        matcher = pattern.matcher("<html><body><img\n \rid=\"laskdasdkj\"\n \rsrc=\"http://dstadler1.org/\"/><img id=\"laskdasdkj\" src=\"http://dstadler2.org/\"/></body></html>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("http://dstadler1.org/", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("http://dstadler2.org/", matcher.group(2));
        matcher = pattern.matcher("<html><body><img\n \t\rid=\"laskdasdkj\"\n \rsrc \n =\r  \"http://dstadler1.org/\"/><img  \r  id=\" laskdasdkj\"    src    =   \"http://dstadler2.org/\"/></body></html>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("http://dstadler1.org/", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("http://dstadler2.org/", matcher.group(2));
        matcher = pattern.matcher("<img alt=\"Chart?ck=xradar&amp;w=120&amp;h=120&amp;c=7fff00|7fff00&amp;m=4&amp;g=0\" src=\"/chart?ck=xradar&amp;w=120&amp;h=120&amp;c=7fff00|7fff00&amp;m=4&amp;g=0.2&amp;l=A,C,S,T&amp;v=3.0,3.0,2.0,2.0\"");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("/chart?ck=xradar&amp;w=120&amp;h=120&amp;c=7fff00|7fff00&amp;m=4&amp;g=0.2&amp;l=A,C,S,T&amp;v=3.0,3.0,2.0,2.0", matcher.group(2));
        matcher = pattern.matcher("<img src=\"file1\"/><img src=\"file2\"/>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file1", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file2", matcher.group(2));
        matcher = pattern.matcher("<img src=\"file1\"/><img src=\"file2\"/><img src=\"file3\"/><img src=\"file4\"/><img src=\"file5\"/>");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file1", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file2", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file3", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file4", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file5", matcher.group(2));
        matcher = pattern.matcher("<img src=\"file1\"><img src=\"file2\">");
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file1", matcher.group(2));
        org.junit.Assert.assertTrue(matcher.find());
        org.junit.Assert.assertEquals("file2", matcher.group(2));
    }

    @org.junit.Test
    public void testEmail127() throws java.lang.Exception {
        java.util.logging.Logger.getLogger(org.apache.commons.mail.ImageHtmlEmail.class.getName()).setLevel(java.util.logging.Level.FINEST);
        getMailServer();
        final java.lang.String strSubject = "Test HTML Send default with URL";
        email = new org.apache.commons.mail.mocks.MockImageHtmlEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setSubject(strSubject);
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(TEST_IMAGE_DIR.toURI().toURL() , TEST_IS_LENIENT));
        email.setHtmlMsg("<html><body><img title=\"$\" src=\"http://www.apache.org/images/feather.gif\"/></body></html>");
        email.send();
        fakeMailServer.stop();
        validateSend(fakeMailServer, strSubject, email.getHtmlMsg(), email.getFromAddress(), email.getToAddresses(), email.getCcAddresses(), email.getBccAddresses(), true);
    }

    private java.lang.String loadUrlContent(final java.net.URL url) throws java.io.IOException {
        final java.io.InputStream stream = url.openStream();
        final java.lang.StringBuilder html = new java.lang.StringBuilder();
        try {
            final java.util.List<java.lang.String> lines = org.apache.commons.io.IOUtils.readLines(stream);
            for (final java.lang.String line : lines) {
                html.append(line).append("\n");
            }
        } finally {
            stream.close();
        }
        return html.toString();
    }

    private static final class MockDataSourceClassPathResolver extends org.apache.commons.mail.resolver.DataSourceClassPathResolver {
        public MockDataSourceClassPathResolver(final java.lang.String classPathBase ,final boolean lenient) {
            super(classPathBase, lenient);
        }

        @java.lang.Override
        public javax.activation.DataSource resolve(java.lang.String resourceLocation, boolean isLenient) throws java.io.IOException {
            javax.mail.util.ByteArrayDataSource ds = ((javax.mail.util.ByteArrayDataSource)(super.resolve(resourceLocation, isLenient)));
            ds.setName(null);
            return ds;
        }
    }
}

