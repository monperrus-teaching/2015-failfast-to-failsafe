package org.apache.commons.mail.util;


public class MimeMessageParserTest {
    @org.junit.Test
    public void testParseSimpleEmail() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/simple.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test HTML Send #1 Subject (wo charset)", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertFalse(mimeMessageParser.hasAttachments());
    }

    @org.junit.Test
    public void testParseSimpleReplyEmail() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/simple-reply.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Re: java.lang.NoClassDefFoundError: org/bouncycastle/asn1/pkcs/PrivateKeyInfo", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertFalse(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("coheigea@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("dev@ws.apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertFalse(mimeMessageParser.hasAttachments());
    }

    @org.junit.Test
    public void testParseHtmlEmailWithAttachments() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/html-attachment.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertTrue(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 2));
        dataSource = mimeMessageParser.findAttachmentByName("Wasserlilien.jpg");
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("image/jpeg", dataSource.getContentType());
        dataSource = mimeMessageParser.findAttachmentByName("it20one.pdf");
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("application/pdf", dataSource.getContentType());
    }

    @org.junit.Test
    public void testParseHtmlEmailWithAttachmentAndEncodedFilename() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/html-attachment-encoded-filename.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test HTML Send #1 Subject (w charset)", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertTrue(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 1));
        dataSource = mimeMessageParser.getAttachmentList().get(0);
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("text/plain", dataSource.getContentType());
        org.junit.Assert.assertEquals("Test Attachment - a>ä, o>ö, u>ü, au>äu", dataSource.getName());
    }

    @org.junit.Test
    public void testParseMultipartReport() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/multipart-report.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Gelesen: ", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertTrue(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertFalse(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 1));
        dataSource = ((javax.activation.DataSource)(attachmentList.get(0)));
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertNull(dataSource.getName());
        org.junit.Assert.assertEquals("message/disposition-notification", dataSource.getContentType());
    }

    @org.junit.Test
    public void testAttachmentOnly() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/attachment-only.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Kunde 100029   Auftrag   3600", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertFalse(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertFalse(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 1));
        dataSource = mimeMessageParser.findAttachmentByName("Kunde 100029   Auftrag   3600.pdf");
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("application/pdf", dataSource.getContentType());
    }

    @org.junit.Test
    public void testParseNoHeaderSeperatorWithOutOfMemory() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/outofmemory-no-header-seperation.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("A corrupt Attachment", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertFalse(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertEquals(mimeMessageParser.getTo().size(), 1);
        org.junit.Assert.assertEquals(mimeMessageParser.getCc().size(), 0);
        org.junit.Assert.assertEquals(mimeMessageParser.getBcc().size(), 0);
    }

    @org.junit.Test
    public void testMultipartTextAttachment() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/multipart-text-attachment.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("test", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 1));
        dataSource = mimeMessageParser.findAttachmentByName("test.txt");
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("text/plain", dataSource.getContentType());
    }

    @org.junit.Test
    public void testMultipartTextAttachmentOnly() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/multipart-text-attachment-only.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("test", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertFalse(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 1));
        dataSource = mimeMessageParser.findAttachmentByName("test.txt");
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("text/plain", dataSource.getContentType());
    }

    @org.junit.Test
    public void testParseHtmlEmailWithHtmlAttachment() throws java.lang.Exception {
        javax.activation.DataSource dataSource;
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/html-attachment-content-disposition.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("test", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        final java.util.List<?> attachmentList = mimeMessageParser.getAttachmentList();
        org.junit.Assert.assertTrue(((attachmentList.size()) == 1));
        dataSource = mimeMessageParser.findAttachmentByName("test.html");
        org.junit.Assert.assertNotNull(dataSource);
        org.junit.Assert.assertEquals("text/html", dataSource.getContentType());
    }

    @org.junit.Test
    public void testParseCreatedHtmlEmailWithNoContent() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final org.apache.commons.mail.HtmlEmail email = new org.apache.commons.mail.HtmlEmail();
        email.setMailSession(session);
        email.setFrom("test_from@apache.org");
        email.setSubject("Test Subject");
        email.addTo("test_to@apache.org");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(msg);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test Subject", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertFalse(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertFalse(mimeMessageParser.hasAttachments());
    }

    @org.junit.Test
    public void testParseCreatedHtmlEmailWithTextContent() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final org.apache.commons.mail.HtmlEmail email = new org.apache.commons.mail.HtmlEmail();
        email.setMailSession(session);
        email.setFrom("test_from@apache.org");
        email.setSubject("Test Subject");
        email.addTo("test_to@apache.org");
        email.setTextMsg("My test message");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(msg);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test Subject", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertFalse(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertFalse(mimeMessageParser.hasAttachments());
    }

    @org.junit.Test
    public void testParseCreatedHtmlEmailWithMixedContent() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final org.apache.commons.mail.HtmlEmail email = new org.apache.commons.mail.HtmlEmail();
        email.setMailSession(session);
        email.setFrom("test_from@apache.org");
        email.setSubject("Test Subject");
        email.addTo("test_to@apache.org");
        email.setTextMsg("My test message");
        email.setHtmlMsg("<p>My HTML message</p>");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(msg);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test Subject", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertTrue(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertTrue(mimeMessageParser.hasPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getPlainContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("test_from@apache.org", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertFalse(mimeMessageParser.hasAttachments());
    }

    @org.junit.Test
    public void testParseInlineCID() throws java.lang.Exception {
        final javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
        final javax.mail.internet.MimeMessage message = org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, new java.io.File("./src/test/resources/eml/html-attachment.eml"));
        final org.apache.commons.mail.util.MimeMessageParser mimeMessageParser = new org.apache.commons.mail.util.MimeMessageParser(message);
        mimeMessageParser.parse();
        org.junit.Assert.assertEquals("Test", mimeMessageParser.getSubject());
        org.junit.Assert.assertNotNull(mimeMessageParser.getMimeMessage());
        org.junit.Assert.assertTrue(mimeMessageParser.isMultipart());
        org.junit.Assert.assertTrue(mimeMessageParser.hasHtmlContent());
        org.junit.Assert.assertNotNull(mimeMessageParser.getHtmlContent());
        org.junit.Assert.assertTrue(((mimeMessageParser.getTo().size()) == 1));
        org.junit.Assert.assertTrue(((mimeMessageParser.getCc().size()) == 0));
        org.junit.Assert.assertTrue(((mimeMessageParser.getBcc().size()) == 0));
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getFrom());
        org.junit.Assert.assertEquals("siegfried.goeschl@it20one.at", mimeMessageParser.getReplyTo());
        org.junit.Assert.assertTrue(mimeMessageParser.hasAttachments());
        org.junit.Assert.assertTrue(mimeMessageParser.getContentIds().contains("part1.01080006.06060206@it20one.at"));
        org.junit.Assert.assertFalse(mimeMessageParser.getContentIds().contains("part2"));
        final javax.activation.DataSource ds = mimeMessageParser.findAttachmentByCid("part1.01080006.06060206@it20one.at");
        org.junit.Assert.assertNotNull(ds);
        org.junit.Assert.assertEquals(ds, mimeMessageParser.getAttachmentList().get(0));
    }
}

