package org.apache.commons.mail;


public class DefaultAuthenticatorTest {
    @org.junit.Test
    public void testDefaultAuthenticatorConstructor() {
        final java.lang.String strUsername = "user.name";
        final java.lang.String strPassword = "user.pwd";
        final org.apache.commons.mail.DefaultAuthenticator authenticator = new org.apache.commons.mail.DefaultAuthenticator(strUsername , strPassword);
        org.junit.Assert.assertTrue(javax.mail.PasswordAuthentication.class.isInstance(authenticator.getPasswordAuthentication()));
        org.junit.Assert.assertEquals(strUsername, authenticator.getPasswordAuthentication().getUserName());
        org.junit.Assert.assertEquals(strPassword, authenticator.getPasswordAuthentication().getPassword());
    }
}

