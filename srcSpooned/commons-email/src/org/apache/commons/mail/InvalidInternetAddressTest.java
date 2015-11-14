package org.apache.commons.mail;


public class InvalidInternetAddressTest extends org.apache.commons.mail.AbstractEmailTest {
    private static final java.lang.String VALID_QUOTED_EMAIL = "\"John O\'Groats\"@domain.com";

    private static java.lang.reflect.Method validateMethod;

    private static final java.lang.String[] ARR_INVALID_EMAILS = new java.lang.String[]{ "local name@domain.com" , "local(name@domain.com" , "local)name@domain.com" , "local<name@domain.com" , "local>name@domain.com" , "local,name@domain.com" , "local;name@domain.com" , "local:name@domain.com" , "local[name@domain.com" , "local]name@domain.com" , "local\tname@domain.com" , "local\nname@domain.com" , "local\rname@domain.com" , "local.name@domain com" , "local.name@domain(com" , "local.name@domain)com" , "local.name@domain<com" , "local.name@domain>com" , "local.name@domain,com" , "local.name@domain;com" , "local.name@domain:com" , "local.name@domain[com" , "local.name@domain]com" , "local.name@domain\\com" , "local.name@domain\tcom" , "local.name@domain\ncom" , "local.name@domain\rcom" , "local.name@" , "@domain.com" };

    @org.junit.Before
    public void setUpInvalidInternetAddressTest() {
        try {
            org.apache.commons.mail.InvalidInternetAddressTest.validateMethod = javax.mail.internet.InternetAddress.class.getMethod("validate", new java.lang.Class[0]);
        } catch (final java.lang.Exception e) {
            org.junit.Assert.assertEquals("Got wrong Exception when looking for validate()", java.lang.NoSuchMethodException.class, e.getClass());
        }
    }

    @org.junit.Test
    public void testStrictConstructor() throws java.lang.Exception {
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            try {
                new javax.mail.internet.InternetAddress(ARR_INVALID_EMAILS[i]);
                org.junit.Assert.fail(((("Strict " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
            } catch (final java.lang.Exception ex) {
            }
        }
        try {
            new javax.mail.internet.InternetAddress(VALID_QUOTED_EMAIL);
        } catch (final java.lang.Exception ex) {
            org.junit.Assert.fail(((("Valid Quoted Email failed: " + (VALID_QUOTED_EMAIL)) + " - ") + (ex.getMessage())));
        }
    }

    @org.junit.Test
    public void testValidateMethod() throws java.lang.Exception {
        if ((org.apache.commons.mail.InvalidInternetAddressTest.validateMethod) == null) {
            return ;
        } 
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            final javax.mail.internet.InternetAddress address = new javax.mail.internet.InternetAddress(ARR_INVALID_EMAILS[i] , "Joe");
            final boolean quoted = ARR_INVALID_EMAILS[i].contains("\"");
            final int atIndex = ARR_INVALID_EMAILS[i].indexOf("@");
            final boolean domainBracket = (atIndex >= 0) && ((ARR_INVALID_EMAILS[i].indexOf("[", atIndex)) >= 0);
            try {
                org.apache.commons.mail.InvalidInternetAddressTest.validateMethod.invoke(address, ((java.lang.Object[])(null)));
                if (!(quoted || domainBracket)) {
                    org.junit.Assert.fail(((("Validate " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
                } 
            } catch (final java.lang.Exception ex) {
                if (quoted || domainBracket) {
                    org.junit.Assert.fail(((((("Validate " + i) + " failed: ") + (ARR_INVALID_EMAILS[i])) + " - ") + (ex.getMessage())));
                } 
            }
        }
        try {
            org.apache.commons.mail.InvalidInternetAddressTest.validateMethod.invoke(new javax.mail.internet.InternetAddress(VALID_QUOTED_EMAIL , "Joe"), ((java.lang.Object[])(null)));
        } catch (final java.lang.Exception ex) {
            org.junit.Assert.fail(((("Valid Quoted Email failed: " + (VALID_QUOTED_EMAIL)) + " - ") + (ex.getMessage())));
        }
    }

    @org.junit.Test
    public void testValidateMethodCharset() throws java.lang.Exception {
        if ((org.apache.commons.mail.InvalidInternetAddressTest.validateMethod) == null) {
            return ;
        } 
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            final javax.mail.internet.InternetAddress address = new javax.mail.internet.InternetAddress(ARR_INVALID_EMAILS[i] , "Joe" , "UTF-8");
            final boolean quoted = ARR_INVALID_EMAILS[i].contains("\"");
            final int atIndex = ARR_INVALID_EMAILS[i].indexOf("@");
            final boolean domainBracket = (atIndex >= 0) && ((ARR_INVALID_EMAILS[i].indexOf("[", atIndex)) >= 0);
            try {
                org.apache.commons.mail.InvalidInternetAddressTest.validateMethod.invoke(address, ((java.lang.Object[])(null)));
                if (!(quoted || domainBracket)) {
                    org.junit.Assert.fail(((("Validate " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
                } 
            } catch (final java.lang.Exception ex) {
                if (quoted || domainBracket) {
                    org.junit.Assert.fail(((((("Validate " + i) + " failed: ") + (ARR_INVALID_EMAILS[i])) + " - ") + (ex.getMessage())));
                } 
            }
        }
        try {
            org.apache.commons.mail.InvalidInternetAddressTest.validateMethod.invoke(new javax.mail.internet.InternetAddress(VALID_QUOTED_EMAIL , "Joe" , "UTF-8"), ((java.lang.Object[])(null)));
        } catch (final java.lang.Exception ex) {
            org.junit.Assert.fail(((("Valid Quoted Email failed: " + (VALID_QUOTED_EMAIL)) + " - ") + (ex.getMessage())));
        }
    }
}

