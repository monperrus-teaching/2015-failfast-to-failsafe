package org.apache.commons.mail;


public class EmailLiveTest extends org.apache.commons.mail.AbstractEmailTest {
    @org.junit.Before
    public void setUpLiveTest() {
        java.lang.System.setProperty("mail.mime.charset", "utf-8");
        java.lang.System.setProperty("mail.mime.encodefilename", "true");
    }

    protected org.apache.commons.mail.Email send(final org.apache.commons.mail.Email email) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.settings.EmailConfiguration.MAIL_FORCE_SEND) {
            email.send();
        } else {
            email.buildMimeMessage();
        }
        return email;
    }

    protected java.lang.String getFromUrl(final java.net.URL url) throws java.lang.Exception {
        final javax.activation.URLDataSource dataSource = new javax.activation.URLDataSource(url);
        final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        org.apache.commons.io.IOUtils.copy(dataSource.getInputStream(), baos);
        return new java.lang.String(baos.toByteArray() , "UTF-8");
    }

    private org.apache.commons.mail.Email create(final java.lang.Class<? extends org.apache.commons.mail.Email> clazz) throws java.lang.Exception {
        final org.apache.commons.mail.Email email = clazz.newInstance();
        email.setStartTLSEnabled(org.apache.commons.mail.settings.EmailConfiguration.MAIL_USE_STARTTLS);
        email.setStartTLSRequired(org.apache.commons.mail.settings.EmailConfiguration.MAIL_STARTTLS_REQUIRED);
        email.setSSLOnConnect(org.apache.commons.mail.settings.EmailConfiguration.MAIL_USE_SSL);
        email.setSSLCheckServerIdentity(org.apache.commons.mail.settings.EmailConfiguration.MAIL_SSL_CHECKSERVERIDENTITY);
        email.setHostName(org.apache.commons.mail.settings.EmailConfiguration.MAIL_SERVER);
        email.setSmtpPort(org.apache.commons.mail.settings.EmailConfiguration.MAIL_SERVER_PORT);
        email.setBounceAddress(org.apache.commons.mail.settings.EmailConfiguration.TEST_FROM);
        email.setDebug(org.apache.commons.mail.settings.EmailConfiguration.MAIL_DEBUG);
        email.setCharset(org.apache.commons.mail.settings.EmailConfiguration.MAIL_CHARSET);
        email.setFrom(org.apache.commons.mail.settings.EmailConfiguration.TEST_FROM);
        email.addTo(org.apache.commons.mail.settings.EmailConfiguration.TEST_TO);
        if ((org.apache.commons.mail.settings.EmailConfiguration.TEST_USER) != null) {
            email.setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(org.apache.commons.mail.settings.EmailConfiguration.TEST_USER , org.apache.commons.mail.settings.EmailConfiguration.TEST_PASSWD));
        } 
        return email;
    }

    @org.junit.Test
    public void testSimpleEmail() throws java.lang.Exception {
        final org.apache.commons.mail.SimpleEmail email = ((org.apache.commons.mail.SimpleEmail)(create(org.apache.commons.mail.SimpleEmail.class)));
        email.setSubject("TestSimpleMail");
        email.setMsg("This is a test mail ... :-)");
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/simplemail.eml"), send(email).getMimeMessage());
    }

    @org.junit.Test
    public void testFoldedHeaderValue() throws java.lang.Exception {
        final org.apache.commons.mail.SimpleEmail email = ((org.apache.commons.mail.SimpleEmail)(create(org.apache.commons.mail.SimpleEmail.class)));
        email.setSubject("TestFoldedHeaderMail");
        email.setMsg("This is a test mail with a folded header value... :-)");
        email.addHeader("X-TestHeader", "This is a very long header value which should be folded into two lines, hopefully");
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/foldedheader.eml"), send(email).getMimeMessage());
    }

    @org.junit.Test
    public void testMultiPartEmail() throws java.lang.Exception {
        final org.apache.commons.mail.MultiPartEmail email = ((org.apache.commons.mail.MultiPartEmail)(create(org.apache.commons.mail.MultiPartEmail.class)));
        email.setSubject("TestMultiPartMail");
        email.setMsg("This is a test mail ... :-)");
        email.attach(new java.io.File("./src/test/resources/attachments/logo.pdf"));
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/multipart.eml"), send(email).getMimeMessage());
    }

    @org.junit.Test
    public void testHtmlMailMimeLayout() throws java.lang.Exception {
        java.lang.String textMsg;
        java.lang.String htmlMsg;
        java.lang.String cid;
        final java.net.URL url = new java.net.URL(org.apache.commons.mail.settings.EmailConfiguration.TEST_URL);
        final java.io.File imageFile = new java.io.File("./src/test/resources/images/asf_logo_wide.gif");
        final org.apache.commons.mail.EmailAttachment attachment = new org.apache.commons.mail.EmailAttachment();
        final java.io.File attachmentFile = new java.io.File("./src/test/resources/attachments/logo.pdf");
        attachment.setName("logo.pdf");
        attachment.setDescription("The official Apache logo");
        attachment.setPath(attachmentFile.getAbsolutePath());
        final org.apache.commons.mail.HtmlEmail htmlEmail1 = ((org.apache.commons.mail.HtmlEmail)(create(org.apache.commons.mail.HtmlEmail.class)));
        textMsg = "Your email client does not support HTML messages";
        htmlMsg = "<html><b>This is a HTML message without any image</b><html>";
        htmlEmail1.setSubject("[email] 1.Test: text + html content");
        htmlEmail1.setTextMsg(textMsg);
        htmlEmail1.setHtmlMsg(htmlMsg);
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/htmlemail1.eml"), send(htmlEmail1).getMimeMessage());
        final org.apache.commons.mail.HtmlEmail htmlEmail2 = ((org.apache.commons.mail.HtmlEmail)(create(org.apache.commons.mail.HtmlEmail.class)));
        textMsg = "Your email client does not support HTML messages";
        htmlMsg = "<html><b>This is a HTML message with an image attachment</b><html>";
        htmlEmail2.setSubject("[email] 2.Test: text + html content + image as attachment");
        htmlEmail2.setTextMsg(textMsg);
        htmlEmail2.setHtmlMsg(htmlMsg);
        htmlEmail2.attach(url, "Apache Logo", "The official Apache logo");
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/htmlemail2.eml"), send(htmlEmail2).getMimeMessage());
        final org.apache.commons.mail.HtmlEmail htmlEmail3 = ((org.apache.commons.mail.HtmlEmail)(create(org.apache.commons.mail.HtmlEmail.class)));
        textMsg = "Your email client does not support HTML messages";
        cid = htmlEmail3.embed(imageFile, "Apache Logo");
        htmlMsg = ("<html><b>This is a HTML message with an inline image - <img src=\"cid:" + cid) + "\"> and NO attachment</b><html>";
        htmlEmail3.setSubject("[email] 3.Test: text + html content + inline image");
        htmlEmail3.setTextMsg(textMsg);
        htmlEmail3.setHtmlMsg(htmlMsg);
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/htmlemail3.eml"), send(htmlEmail3).getMimeMessage());
        final org.apache.commons.mail.HtmlEmail htmlEmail4 = ((org.apache.commons.mail.HtmlEmail)(create(org.apache.commons.mail.HtmlEmail.class)));
        textMsg = "Your email client does not support HTML messages";
        cid = htmlEmail4.embed(imageFile, "Apache Logo");
        htmlMsg = ("<html><b>This is a HTML message with an inline image - <img src=\"cid:" + cid) + "\"> and attachment</b><html>";
        htmlEmail4.setSubject("[email] 4.Test: text + html content + inline image + attachment");
        htmlEmail4.setTextMsg(textMsg);
        htmlEmail4.setHtmlMsg(htmlMsg);
        htmlEmail4.attach(attachment);
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/htmlemail4.eml"), send(htmlEmail4).getMimeMessage());
    }

    @org.junit.Test
    public void testCorrectCharacterEncoding() throws java.lang.Exception {
        final java.lang.String subject = "[email] 5.Test: Subject with three greek UTF-8 characters : αβγ";
        final java.lang.String textMsg = "My test body with with three greek UTF-8 characters : αβγ\n";
        final java.lang.String attachmentName = "αβγ.txt";
        final org.apache.commons.mail.MultiPartEmail email = ((org.apache.commons.mail.MultiPartEmail)(create(org.apache.commons.mail.MultiPartEmail.class)));
        email.setSubject(subject);
        email.setMsg(textMsg);
        final javax.activation.DataSource attachment = new javax.mail.util.ByteArrayDataSource(textMsg.getBytes("utf-8") , "text/plain");
        email.attach(attachment, attachmentName, "Attachment in Greek");
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/correct-encoding.eml"), send(email).getMimeMessage());
    }

    @org.junit.Test
    public void testImageHtmlEmailLocal() throws java.lang.Exception {
        final java.io.File htmlFile = new java.io.File("./src/test/resources/html/www.apache.org.html");
        final java.lang.String htmlMsg1 = org.apache.commons.io.FileUtils.readFileToString(htmlFile);
        final org.apache.commons.mail.ImageHtmlEmail email = ((org.apache.commons.mail.ImageHtmlEmail)(create(org.apache.commons.mail.ImageHtmlEmail.class)));
        email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(htmlFile.getParentFile().toURI().toURL() , false));
        email.setSubject("[testImageHtmlEmail] 1.Test: simple html content");
        email.setHtmlMsg(htmlMsg1);
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/testImageHtmlEmailLocal.eml"), send(email).getMimeMessage());
    }

    @org.junit.Test
    public void testImageHtmlEmailRemote() throws java.lang.Exception {
        if (org.apache.commons.mail.settings.EmailConfiguration.MAIL_FORCE_SEND) {
            final java.net.URL url = new java.net.URL("http://commons.apache.org/email/");
            final java.lang.String htmlMsg = getFromUrl(url);
            final org.apache.commons.mail.ImageHtmlEmail email = ((org.apache.commons.mail.ImageHtmlEmail)(create(org.apache.commons.mail.ImageHtmlEmail.class)));
            email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(url , true));
            email.setSubject("[testImageHtmlEmail] 2.Test: complex html content");
            email.setHtmlMsg(htmlMsg);
            org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/testImageHtmlEmailRemote.eml"), send(email).getMimeMessage());
        } 
    }

    @org.junit.Test
    public void testSendingEmailsInBatch() throws java.lang.Exception {
        final java.util.List<org.apache.commons.mail.SimpleEmail> emails = new java.util.ArrayList<org.apache.commons.mail.SimpleEmail>();
        final javax.mail.Session session = create(org.apache.commons.mail.SimpleEmail.class).getMailSession();
        final javax.mail.Transport transport = session.getTransport();
        for (int i = 0 ; i < 3 ; i++) {
            final org.apache.commons.mail.SimpleEmail personalizedEmail = ((org.apache.commons.mail.SimpleEmail)(create(org.apache.commons.mail.SimpleEmail.class)));
            personalizedEmail.setMailSession(session);
            personalizedEmail.setSubject(("Personalized Test Mail Nr. " + i));
            personalizedEmail.setMsg("This is a personalized test mail ... :-)");
            personalizedEmail.buildMimeMessage();
            emails.add(personalizedEmail);
        }
        if (org.apache.commons.mail.settings.EmailConfiguration.MAIL_FORCE_SEND) {
            transport.connect();
            for (final org.apache.commons.mail.SimpleEmail personalizedEmail : emails) {
                final javax.mail.internet.MimeMessage mimeMessage = personalizedEmail.getMimeMessage();
                javax.mail.Transport.send(mimeMessage);
                java.lang.System.out.println(("Successfully sent the following email : " + (mimeMessage.getMessageID())));
            }
            transport.close();
        } 
    }

    @org.junit.Test
    public void testPartialSend() throws java.lang.Exception {
        final org.apache.commons.mail.SimpleEmail email = ((org.apache.commons.mail.SimpleEmail)(create(org.apache.commons.mail.SimpleEmail.class)));
        email.addTo(org.apache.commons.mail.settings.EmailConfiguration.TEST_TO);
        email.addTo("nobody@is.invalid");
        email.setSubject("TestPartialMail");
        email.setMsg("This is a test mail ... :-)");
        email.setSendPartial(true);
        org.apache.commons.mail.EmailUtils.writeMimeMessage(new java.io.File("./target/test-emails/partialmail.eml"), send(email).getMimeMessage());
    }
}

