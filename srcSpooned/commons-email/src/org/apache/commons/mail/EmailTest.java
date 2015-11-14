package org.apache.commons.mail;


public class EmailTest extends org.apache.commons.mail.AbstractEmailTest {
    private static final java.lang.String[] VALID_EMAILS = new java.lang.String[]{ "me@home.com" , "joe.doe@apache.org" , "someone_here@work-address.com.au" };

    private org.apache.commons.mail.mocks.MockEmailConcrete email;

    @org.junit.Before
    public void setUpEmailTest() {
        email = new org.apache.commons.mail.mocks.MockEmailConcrete();
    }

    @org.junit.Test
    public void testGetSetDebug() {
        email.setDebug(true);
        org.junit.Assert.assertTrue(email.isDebug());
        email.setDebug(false);
        org.junit.Assert.assertFalse(email.isDebug());
    }

    @org.junit.Test
    public void testGetSetSession() throws java.lang.Exception {
        final java.util.Properties properties = new java.util.Properties(java.lang.System.getProperties());
        properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_TRANSPORT_PROTOCOL, org.apache.commons.mail.EmailConstants.SMTP);
        properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_PORT, java.lang.String.valueOf(getMailServerPort()));
        properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_HOST, strTestMailServer);
        properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_DEBUG, java.lang.String.valueOf(false));
        final javax.mail.Session mySession = javax.mail.Session.getInstance(properties, null);
        email.setMailSession(mySession);
        org.junit.Assert.assertEquals(mySession, email.getMailSession());
    }

    @org.junit.Test
    public void testGetSetAuthentication() {
        final java.lang.String strUsername = "user.name";
        final java.lang.String strPassword = "user.pwd";
        email.setAuthentication(strUsername, strPassword);
        final org.apache.commons.mail.DefaultAuthenticator retrievedAuth = ((org.apache.commons.mail.DefaultAuthenticator)(email.getAuthenticator()));
        org.junit.Assert.assertEquals(strUsername, retrievedAuth.getPasswordAuthentication().getUserName());
        org.junit.Assert.assertEquals(strPassword, retrievedAuth.getPasswordAuthentication().getPassword());
    }

    @org.junit.Test
    public void testGetSetAuthenticator() {
        final java.lang.String strUsername = "user.name";
        final java.lang.String strPassword = "user.pwd";
        final org.apache.commons.mail.DefaultAuthenticator authenticator = new org.apache.commons.mail.DefaultAuthenticator(strUsername , strPassword);
        email.setAuthenticator(authenticator);
        final org.apache.commons.mail.DefaultAuthenticator retrievedAuth = ((org.apache.commons.mail.DefaultAuthenticator)(email.getAuthenticator()));
        org.junit.Assert.assertEquals(strUsername, retrievedAuth.getPasswordAuthentication().getUserName());
        org.junit.Assert.assertEquals(strPassword, retrievedAuth.getPasswordAuthentication().getPassword());
    }

    @org.junit.Test
    public void testGetSetCharset() {
        java.nio.charset.Charset set = java.nio.charset.Charset.forName("US-ASCII");
        email.setCharset(set.name());
        org.junit.Assert.assertEquals(set.name(), email.getCharset());
        set = java.nio.charset.Charset.forName("UTF-8");
        email.setCharset(set.name());
        org.junit.Assert.assertEquals(set.name(), email.getCharset());
    }

    @org.junit.Test
    public void testSetContentEmptyMimeMultipart() {
        final javax.mail.internet.MimeMultipart part = new javax.mail.internet.MimeMultipart();
        email.setContent(part);
        org.junit.Assert.assertEquals(part, email.getContentMimeMultipart());
    }

    @org.junit.Test
    public void testSetContentMimeMultipart() {
        final javax.mail.internet.MimeMultipart part = new javax.mail.internet.MimeMultipart("abc123");
        email.setContent(part);
        org.junit.Assert.assertEquals(part, email.getContentMimeMultipart());
    }

    @org.junit.Test
    public void testSetContentNull() throws java.lang.Exception {
        email.setContent(null);
        org.junit.Assert.assertNull(email.getContentMimeMultipart());
    }

    @org.junit.Test
    public void testSetContentObject() {
        java.lang.String testObject = "test string object";
        java.lang.String testContentType = " ; charset=" + (org.apache.commons.mail.EmailConstants.US_ASCII);
        email.setContent(testObject, testContentType);
        org.junit.Assert.assertEquals(testObject, email.getContentObject());
        org.junit.Assert.assertEquals(testContentType, email.getContentType());
        testObject = null;
        testContentType = (" ; charset=" + (org.apache.commons.mail.EmailConstants.US_ASCII)) + " some more here";
        email.setContent(testObject, testContentType);
        org.junit.Assert.assertEquals(testObject, email.getContentObject());
        org.junit.Assert.assertEquals(testContentType, email.getContentType());
        testObject = "test string object";
        testContentType = null;
        email.setContent(testObject, testContentType);
        org.junit.Assert.assertEquals(testObject, email.getContentObject());
        org.junit.Assert.assertEquals(testContentType, email.getContentType());
        testObject = "test string object";
        testContentType = " something incorrect ";
        email.setContent(testObject, testContentType);
        org.junit.Assert.assertEquals(testObject, email.getContentObject());
        org.junit.Assert.assertEquals(testContentType, email.getContentType());
    }

    @org.junit.Test
    public void testGetSetHostName() {
        for (final java.lang.String validChar : testCharsValid) {
            email.setHostName(validChar);
            org.junit.Assert.assertEquals(validChar, email.getHostName());
        }
    }

    @org.junit.Test
    public void testGetSetSmtpPort() {
        email.setSmtpPort(1);
        org.junit.Assert.assertEquals(1, java.lang.Integer.valueOf(email.getSmtpPort()).intValue());
        email.setSmtpPort(java.lang.Integer.MAX_VALUE);
        org.junit.Assert.assertEquals(java.lang.Integer.MAX_VALUE, java.lang.Integer.valueOf(email.getSmtpPort()).intValue());
    }

    @org.junit.Test
    public void testSetSmtpPortZero() {
        email.setSmtpPort(0);
    }

    @org.junit.Test
    public void testSetSmptPortNegative() {
        email.setSmtpPort(-1);
    }

    @org.junit.Test
    public void testSetSmtpPortMinValue() {
        email.setSmtpPort(java.lang.Integer.MIN_VALUE);
    }

    @org.junit.Test
    public void testSetFrom() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org" , "joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au" , "someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.setFrom(VALID_EMAILS[i]);
            org.junit.Assert.assertEquals(arrExpected.get(i), email.getFromAddress());
        }
    }

    @org.junit.Test
    public void testSetFromWithEncoding() throws java.lang.Exception {
        final java.lang.String testValidEmail = "me@home.com";
        final javax.mail.internet.InternetAddress inetExpected = new javax.mail.internet.InternetAddress("me@home.com" , "me@home.com" , org.apache.commons.mail.EmailConstants.ISO_8859_1);
        email.setFrom(testValidEmail, testValidEmail, org.apache.commons.mail.EmailConstants.ISO_8859_1);
        org.junit.Assert.assertEquals(inetExpected, email.getFromAddress());
    }

    @org.junit.Test
    public void testSetFrom2() throws java.lang.Exception {
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org" , "joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au" , "someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.setFrom(VALID_EMAILS[i], testEmailNames[i]);
            org.junit.Assert.assertEquals(arrExpected.get(i), email.getFromAddress());
        }
    }

    @org.junit.Test
    public void testSetFromBadEncoding() throws java.lang.Exception {
        email.setFrom("me@home.com", "me@home.com", "bad.encoding여\n");
    }

    @org.junit.Test
    public void testAddTo() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (final java.lang.String address : VALID_EMAILS) {
            email.addTo(address);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getToAddresses().toString());
    }

    @org.junit.Test
    public void testAddToArray() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        email.addTo(VALID_EMAILS);
        org.junit.Assert.assertEquals(arrExpected.size(), email.getToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getToAddresses().toString());
    }

    @org.junit.Test
    public void testAddToWithEncoding() throws java.lang.Exception {
        final java.lang.String testCharset = org.apache.commons.mail.EmailConstants.ISO_8859_1;
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1" , testCharset));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addTo(VALID_EMAILS[i], testEmailNames[i], testCharset);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getToAddresses().toString());
    }

    @org.junit.Test
    public void testAddTo2() throws java.lang.Exception {
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addTo(VALID_EMAILS[i], testEmailNames[i]);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getToAddresses().toString());
    }

    @org.junit.Test
    public void testAddToBadEncoding() throws java.lang.Exception {
        email.addTo("me@home.com", "me@home.com", "bad.encoding여\n");
    }

    @org.junit.Test
    public void testSetTo() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> testEmailValid2 = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        testEmailValid2.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        testEmailValid2.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org" , "joe.doe@apache.org"));
        testEmailValid2.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au" , "someone_here@work-address.com.au"));
        email.setTo(testEmailValid2);
        org.junit.Assert.assertEquals(testEmailValid2.size(), email.getToAddresses().size());
        org.junit.Assert.assertEquals(testEmailValid2.toString(), email.getToAddresses().toString());
    }

    @org.junit.Test
    public void testSetToNull() throws java.lang.Exception {
        email.setTo(null);
    }

    @org.junit.Test
    public void testSetToEmpty() throws java.lang.Exception {
        email.setTo(java.util.Collections.<javax.mail.internet.InternetAddress>emptyList());
    }

    @org.junit.Test
    public void testAddCc() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (final java.lang.String address : VALID_EMAILS) {
            email.addCc(address);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getCcAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getCcAddresses().toString());
    }

    @org.junit.Test
    public void testAddCcArray() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        email.addCc(VALID_EMAILS);
        org.junit.Assert.assertEquals(arrExpected.size(), email.getCcAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getCcAddresses().toString());
    }

    @org.junit.Test
    public void testAddCcWithEncoding() throws java.lang.Exception {
        final java.lang.String testCharset = org.apache.commons.mail.EmailConstants.ISO_8859_1;
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1" , testCharset));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addCc(VALID_EMAILS[i], testEmailNames[i], testCharset);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getCcAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getCcAddresses().toString());
    }

    @org.junit.Test
    public void testAddCc2() throws java.lang.Exception {
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addCc(VALID_EMAILS[i], testEmailNames[i]);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getCcAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getCcAddresses().toString());
    }

    @org.junit.Test
    public void testAddCcBadEncoding() throws java.lang.Exception {
        email.addCc("me@home.com", "me@home.com", "bad.encoding여\n");
    }

    @org.junit.Test
    public void testSetCc() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> testEmailValid2 = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        testEmailValid2.add(new javax.mail.internet.InternetAddress("Name1 <me@home.com>"));
        testEmailValid2.add(new javax.mail.internet.InternetAddress("\"joe.doe@apache.org\" <joe.doe@apache.org>"));
        testEmailValid2.add(new javax.mail.internet.InternetAddress("\"someone_here@work.com.au\" <someone_here@work.com.au>"));
        email.setCc(testEmailValid2);
        org.junit.Assert.assertEquals(testEmailValid2, email.getCcAddresses());
    }

    @org.junit.Test
    public void testSetCcNull() throws java.lang.Exception {
        email.setCc(null);
    }

    @org.junit.Test
    public void testSetCcEmpty() throws java.lang.Exception {
        email.setCc(java.util.Collections.<javax.mail.internet.InternetAddress>emptyList());
    }

    @org.junit.Test
    public void testAddBcc() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (final java.lang.String address : VALID_EMAILS) {
            email.addBcc(address);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getBccAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getBccAddresses().toString());
    }

    @org.junit.Test
    public void testAddBccArray() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        email.addBcc(VALID_EMAILS);
        org.junit.Assert.assertEquals(arrExpected.size(), email.getBccAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getBccAddresses().toString());
    }

    @org.junit.Test
    public void testAddBccWithEncoding() throws java.lang.Exception {
        final java.lang.String testCharset = org.apache.commons.mail.EmailConstants.ISO_8859_1;
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1" , testCharset));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addBcc(VALID_EMAILS[i], testEmailNames[i], testCharset);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getBccAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getBccAddresses().toString());
    }

    @org.junit.Test
    public void testAddBcc2() throws java.lang.Exception {
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addBcc(VALID_EMAILS[i], testEmailNames[i]);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getBccAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getBccAddresses().toString());
    }

    @org.junit.Test
    public void testAddBccBadEncoding() throws java.lang.Exception {
        email.addBcc("me@home.com", "me@home.com", "bad.encoding여\n");
    }

    @org.junit.Test
    public void testSetBcc() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> testInetEmailValid = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        testInetEmailValid.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        testInetEmailValid.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org" , "joe.doe@apache.org"));
        testInetEmailValid.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au" , "someone_here@work-address.com.au"));
        email.setBcc(testInetEmailValid);
        org.junit.Assert.assertEquals(testInetEmailValid, email.getBccAddresses());
    }

    @org.junit.Test
    public void testSetBccNull() throws java.lang.Exception {
        email.setBcc(null);
    }

    @org.junit.Test
    public void testSetBccEmpty() throws java.lang.Exception {
        email.setBcc(java.util.Collections.<javax.mail.internet.InternetAddress>emptyList());
    }

    @org.junit.Test
    public void testAddReplyTo() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (final java.lang.String address : VALID_EMAILS) {
            email.addReplyTo(address);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getReplyToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getReplyToAddresses().toString());
    }

    @org.junit.Test
    public void testAddReplyToWithEncoding() throws java.lang.Exception {
        final java.lang.String testCharset = org.apache.commons.mail.EmailConstants.ISO_8859_1;
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1" , testCharset));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addReplyTo(VALID_EMAILS[i], testEmailNames[i], testCharset);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getReplyToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getReplyToAddresses().toString());
    }

    @org.junit.Test
    public void testAddReplyTo2() throws java.lang.Exception {
        final java.lang.String[] testEmailNames = new java.lang.String[]{ "Name1" , "" , null };
        final java.util.List<javax.mail.internet.InternetAddress> arrExpected = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        arrExpected.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        arrExpected.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org"));
        arrExpected.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au"));
        for (int i = 0 ; i < (VALID_EMAILS.length) ; i++) {
            email.addReplyTo(VALID_EMAILS[i], testEmailNames[i]);
        }
        org.junit.Assert.assertEquals(arrExpected.size(), email.getReplyToAddresses().size());
        org.junit.Assert.assertEquals(arrExpected.toString(), email.getReplyToAddresses().toString());
    }

    @org.junit.Test
    public void testAddReplyToBadEncoding() throws java.lang.Exception {
        email.addReplyTo("me@home.com", "me@home.com", "bad.encoding여\n");
    }

    @org.junit.Test
    public void testAddHeader() {
        final java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<java.lang.String, java.lang.String>();
        headers.put("X-Priority", "1");
        headers.put("Disposition-Notification-To", "me@home.com");
        headers.put("X-Mailer", "Sendmail");
        for (final java.util.Map.Entry<java.lang.String, java.lang.String> header : headers.entrySet()) {
            final java.lang.String name = header.getKey();
            final java.lang.String value = header.getValue();
            email.addHeader(name, value);
        }
        org.junit.Assert.assertEquals(headers.size(), email.getHeaders().size());
        org.junit.Assert.assertEquals(headers, email.getHeaders());
    }

    @org.junit.Test
    public void testAddHeaderEmptyName() throws java.lang.Exception {
        email.addHeader("", "me@home.com");
    }

    @org.junit.Test
    public void testAddHeaderNullName() throws java.lang.Exception {
        email.addHeader(null, "me@home.com");
    }

    @org.junit.Test
    public void testAddHeaderEmptyValue() throws java.lang.Exception {
        email.addHeader("X-Mailer", "");
    }

    @org.junit.Test
    public void testAddHeaderNullValue() throws java.lang.Exception {
        email.addHeader("X-Mailer", null);
    }

    @org.junit.Test
    public void testSetHeaders() {
        final java.util.Map<java.lang.String, java.lang.String> ht = new java.util.Hashtable<java.lang.String, java.lang.String>();
        ht.put("X-Priority", "1");
        ht.put("Disposition-Notification-To", "me@home.com");
        ht.put("X-Mailer", "Sendmail");
        email.setHeaders(ht);
        org.junit.Assert.assertEquals(ht.size(), email.getHeaders().size());
        org.junit.Assert.assertEquals(ht, email.getHeaders());
    }

    @org.junit.Test
    public void testFoldingHeaders() throws java.lang.Exception {
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom("a@b.com");
        email.addTo("c@d.com");
        email.setSubject("test mail");
        final java.lang.String headerValue = "1234567890 1234567890 123456789 01234567890 123456789 0123456789 01234567890 01234567890";
        email.addHeader("X-LongHeader", headerValue);
        org.junit.Assert.assertTrue(((email.getHeaders().size()) == 1));
        org.junit.Assert.assertFalse(email.getHeaders().get("X-LongHeader").contains("\r\n"));
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        msg.saveChanges();
        final java.lang.String[] values = msg.getHeader("X-LongHeader");
        org.junit.Assert.assertEquals(1, values.length);
        final java.lang.String[] lines = values[0].split("\\r\\n");
        org.junit.Assert.assertEquals(2, lines.length);
        org.junit.Assert.assertTrue(((values[0].indexOf("\n")) == (values[0].lastIndexOf("\n"))));
    }

    @org.junit.Test
    public void testSetHeaderEmptyValue() throws java.lang.Exception {
        email.setHeaders(java.util.Collections.singletonMap("X-Mailer", ""));
    }

    @org.junit.Test
    public void testSetHeaderNullValue() throws java.lang.Exception {
        email.setHeaders(java.util.Collections.singletonMap("X-Mailer", ((java.lang.String)(null))));
    }

    @org.junit.Test
    public void testSetHeaderEmptyName() throws java.lang.Exception {
        email.setHeaders(java.util.Collections.singletonMap("", "me@home.com"));
    }

    @org.junit.Test
    public void testSetHeaderNullName() throws java.lang.Exception {
        email.setHeaders(java.util.Collections.singletonMap(((java.lang.String)(null)), "me@home.com"));
    }

    @org.junit.Test
    public void testSetSubject() {
        for (final java.lang.String validChar : testCharsValid) {
            email.setSubject(validChar);
            org.junit.Assert.assertEquals(validChar, email.getSubject());
        }
    }

    @org.junit.Test
    public void testSendNoHostName() throws java.lang.Exception {
        getMailServer();
        email = new org.apache.commons.mail.mocks.MockEmailConcrete();
        email.send();
    }

    @org.junit.Test
    public void testSendBadHostName() {
        try {
            getMailServer();
            email = new org.apache.commons.mail.mocks.MockEmailConcrete();
            email.setSubject("Test Email #1 Subject");
            email.setHostName("bad.host.com");
            email.setFrom("me@home.com");
            email.addTo("me@home.com");
            email.addCc("me@home.com");
            email.addBcc("me@home.com");
            email.addReplyTo("me@home.com");
            email.setContent("test string object", (" ; charset=" + (org.apache.commons.mail.EmailConstants.US_ASCII)));
            email.send();
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(((e.getCause()) instanceof javax.mail.internet.ParseException));
            fakeMailServer.stop();
        }
    }

    @org.junit.Test
    public void testSendFromNotSet() throws java.lang.Exception {
        getMailServer();
        email = new org.apache.commons.mail.mocks.MockEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.addTo("me@home.com");
        email.send();
    }

    @org.junit.Test
    public void testSendFromSetInSession() throws java.lang.Exception {
        getMailServer();
        email = new org.apache.commons.mail.mocks.MockEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.addTo("me@home.com");
        email.getSession().getProperties().setProperty(org.apache.commons.mail.EmailConstants.MAIL_FROM, "me@home.com");
        email.send();
    }

    @org.junit.Test
    public void testSendDestinationNotSet() throws java.lang.Exception {
        getMailServer();
        email = new org.apache.commons.mail.mocks.MockEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom("me@home.com");
        email.send();
    }

    @org.junit.Test
    public void testSendBadAuthSet() throws java.lang.Exception {
        getMailServer();
        email = new org.apache.commons.mail.mocks.MockEmailConcrete();
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom(strTestMailFrom);
        email.addTo(strTestMailTo);
        email.setAuthentication(null, null);
        email.send();
    }

    @org.junit.Test
    public void testSendCorrectSmtpPortContainedInException() {
        try {
            getMailServer();
            email = new org.apache.commons.mail.mocks.MockEmailConcrete();
            email.setHostName("bad.host.com");
            email.setSSLOnConnect(true);
            email.setFrom(strTestMailFrom);
            email.addTo(strTestMailTo);
            email.setAuthentication(null, null);
            email.send();
            org.junit.Assert.fail("Should have thrown an exception");
        } catch (final org.apache.commons.mail.EmailException e) {
            org.junit.Assert.assertTrue(e.getMessage().contains("bad.host.com:465"));
            fakeMailServer.stop();
        }
    }

    @org.junit.Test
    public void testGetSetSentDate() {
        final java.util.Date dtTest = java.util.Calendar.getInstance().getTime();
        email.setSentDate(dtTest);
        org.junit.Assert.assertEquals(dtTest, email.getSentDate());
        email.setSentDate(null);
        final java.util.Date sentDate = email.getSentDate();
        org.junit.Assert.assertTrue(((java.lang.Math.abs(((sentDate.getTime()) - (dtTest.getTime())))) < 1000));
    }

    @org.junit.Test
    public void testToInternetAddressArray() throws java.lang.Exception {
        final java.util.List<javax.mail.internet.InternetAddress> testInetEmailValid = new java.util.ArrayList<javax.mail.internet.InternetAddress>();
        testInetEmailValid.add(new javax.mail.internet.InternetAddress("me@home.com" , "Name1"));
        testInetEmailValid.add(new javax.mail.internet.InternetAddress("joe.doe@apache.org" , "joe.doe@apache.org"));
        testInetEmailValid.add(new javax.mail.internet.InternetAddress("someone_here@work-address.com.au" , "someone_here@work-address.com.au"));
        email.setBcc(testInetEmailValid);
        org.junit.Assert.assertEquals(testInetEmailValid.size(), email.getBccAddresses().size());
    }

    @org.junit.Test
    public void testSetPopBeforeSmtp() {
        final boolean boolPopBeforeSmtp = true;
        final java.lang.String strHost = "mail.home.com";
        final java.lang.String strUsername = "user.name";
        final java.lang.String strPassword = "user.passwd";
        email.setPopBeforeSmtp(boolPopBeforeSmtp, strHost, strUsername, strPassword);
        org.junit.Assert.assertEquals(boolPopBeforeSmtp, email.isPopBeforeSmtp());
        org.junit.Assert.assertEquals(strHost, email.getPopHost());
        org.junit.Assert.assertEquals(strUsername, email.getPopUsername());
        org.junit.Assert.assertEquals(strPassword, email.getPopPassword());
    }

    @org.junit.Test
    public void testDefaultCharsetAppliesToTextContent() throws java.lang.Exception {
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom("a@b.com");
        email.addTo("c@d.com");
        email.setSubject("test mail");
        email.setCharset("ISO-8859-1");
        email.setContent("test content", "text/plain");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        msg.saveChanges();
        org.junit.Assert.assertEquals("text/plain; charset=ISO-8859-1", msg.getContentType());
    }

    @org.junit.Test
    public void testDefaultCharsetCanBeOverriddenByContentType() throws java.lang.Exception {
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom("a@b.com");
        email.addTo("c@d.com");
        email.setSubject("test mail");
        email.setCharset("ISO-8859-1");
        email.setContent("test content", "text/plain; charset=US-ASCII");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        msg.saveChanges();
        org.junit.Assert.assertEquals("text/plain; charset=US-ASCII", msg.getContentType());
    }

    @org.junit.Test
    public void testDefaultCharsetIgnoredByNonTextContent() throws java.lang.Exception {
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom("a@b.com");
        email.addTo("c@d.com");
        email.setSubject("test mail");
        email.setCharset("ISO-8859-1");
        email.setContent("test content", "application/octet-stream");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        msg.saveChanges();
        org.junit.Assert.assertEquals("application/octet-stream", msg.getContentType());
    }

    @org.junit.Test
    public void testCorrectContentTypeForPNG() throws java.lang.Exception {
        email.setHostName(strTestMailServer);
        email.setSmtpPort(getMailServerPort());
        email.setFrom("a@b.com");
        email.addTo("c@d.com");
        email.setSubject("test mail");
        email.setCharset("ISO-8859-1");
        final java.io.File png = new java.io.File("./target/test-classes/images/logos/maven-feather.png");
        email.setContent(png, "image/png");
        email.buildMimeMessage();
        final javax.mail.internet.MimeMessage msg = email.getMimeMessage();
        msg.saveChanges();
        org.junit.Assert.assertEquals("image/png", msg.getContentType());
    }

    @org.junit.Test
    public void testGetSetBounceAddress() {
        org.junit.Assert.assertNull(email.getBounceAddress());
        final java.lang.String bounceAddress = "test_bounce@apache.org";
        email.setBounceAddress(bounceAddress);
        org.junit.Assert.assertEquals(bounceAddress, email.getBounceAddress());
    }
}

