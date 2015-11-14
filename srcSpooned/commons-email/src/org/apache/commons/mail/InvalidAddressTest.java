package org.apache.commons.mail;


public class InvalidAddressTest extends org.apache.commons.mail.AbstractEmailTest {
    private static final java.lang.String[] ARR_INVALID_EMAILS = new java.lang.String[]{ "local name@domain.com" , "local(name@domain.com" , "local)name@domain.com" , "local<name@domain.com" , "local>name@domain.com" , "local,name@domain.com" , "local;name@domain.com" , "local:name@domain.com" , "local[name@domain.com" , "local]name@domain.com" , "local\"name@domain.com" , "local\tname@domain.com" , "local\nname@domain.com" , "local\rname@domain.com" , "local.name@domain com" , "local.name@domain(com" , "local.name@domain)com" , "local.name@domain<com" , "local.name@domain>com" , "local.name@domain,com" , "local.name@domain;com" , "local.name@domain:com" , "local.name@domain]com" , "local.name@domain\\com" , "local.name@domain\tcom" , "local.name@domain\ncom" , "local.name@domain\rcom" , "local.name@" , "@domain.com" };

    private org.apache.commons.mail.mocks.MockEmailConcrete email;

    @org.junit.Before
    public void setUpInvalidAddressTest() {
        this.email = new org.apache.commons.mail.mocks.MockEmailConcrete();
    }

    @org.junit.Test
    public void testSetInvalidFrom() throws java.lang.Exception {
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            try {
                email.setFrom(ARR_INVALID_EMAILS[i]);
                org.junit.Assert.fail(((("setFrom " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
            } catch (final org.apache.commons.mail.EmailException ignore) {
            }
        }
    }

    @org.junit.Test
    public void testAddInvalidTo() throws java.lang.Exception {
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            try {
                email.addTo(ARR_INVALID_EMAILS[i], "Joe");
                org.junit.Assert.fail(((("addTo " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
            } catch (final org.apache.commons.mail.EmailException ignore) {
            }
        }
    }

    @org.junit.Test
    public void testAddInvalidCc() throws java.lang.Exception {
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            try {
                email.addCc(ARR_INVALID_EMAILS[i], "Joe");
                org.junit.Assert.fail(((("addCc " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
            } catch (final org.apache.commons.mail.EmailException ignore) {
            }
        }
    }

    @org.junit.Test
    public void testAddInvalidBcc() throws java.lang.Exception {
        for (int i = 0 ; i < (ARR_INVALID_EMAILS.length) ; i++) {
            try {
                email.addBcc(ARR_INVALID_EMAILS[i], "Joe");
                org.junit.Assert.fail(((("addBcc " + i) + " passed: ") + (ARR_INVALID_EMAILS[i])));
            } catch (final org.apache.commons.mail.EmailException ignore) {
            }
        }
    }
}

