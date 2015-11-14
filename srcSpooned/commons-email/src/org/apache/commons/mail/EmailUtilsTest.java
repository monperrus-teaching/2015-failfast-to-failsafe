package org.apache.commons.mail;


public class EmailUtilsTest {
    @org.junit.Test
    public void testUrlEncoding() throws java.io.UnsupportedEncodingException {
        org.junit.Assert.assertEquals("abcdefg", org.apache.commons.mail.EmailUtils.encodeUrl("abcdefg"));
        org.junit.Assert.assertEquals("0123456789", org.apache.commons.mail.EmailUtils.encodeUrl("0123456789"));
        org.junit.Assert.assertEquals("Test%20CID", org.apache.commons.mail.EmailUtils.encodeUrl("Test CID"));
        org.junit.Assert.assertEquals("joe.doe@apache.org", org.apache.commons.mail.EmailUtils.encodeUrl("joe.doe@apache.org"));
        org.junit.Assert.assertEquals("joe+doe@apache.org", org.apache.commons.mail.EmailUtils.encodeUrl("joe+doe@apache.org"));
        org.junit.Assert.assertEquals("peter%26paul%26mary@oldmusic.org", org.apache.commons.mail.EmailUtils.encodeUrl("peter&paul&mary@oldmusic.org"));
    }
}

