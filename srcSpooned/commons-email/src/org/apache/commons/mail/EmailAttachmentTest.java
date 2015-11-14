package org.apache.commons.mail;


public class EmailAttachmentTest extends org.apache.commons.mail.AbstractEmailTest {
    private org.apache.commons.mail.EmailAttachment attachment;

    @org.junit.Before
    public void setUpAttachmentTest() {
        attachment = new org.apache.commons.mail.EmailAttachment();
    }

    @org.junit.Test
    public void testGetSetDescription() {
        for (final java.lang.String validChar : testCharsValid) {
            attachment.setDescription(validChar);
            org.junit.Assert.assertEquals(validChar, attachment.getDescription());
        }
    }

    @org.junit.Test
    public void testGetSetName() {
        for (final java.lang.String validChar : testCharsValid) {
            attachment.setName(validChar);
            org.junit.Assert.assertEquals(validChar, attachment.getName());
        }
    }

    @org.junit.Test
    public void testGetSetPath() {
        for (final java.lang.String validChar : testCharsValid) {
            attachment.setPath(validChar);
            org.junit.Assert.assertEquals(validChar, attachment.getPath());
        }
    }

    @org.junit.Test
    public void testGetSetURL() throws java.lang.Exception {
        final java.lang.String[] tests = new java.lang.String[]{ "http://localhost/" , "http://www.apache.org/" , "http://foo.notexisting.org" };
        for (final java.lang.String urlString : tests) {
            final java.net.URL testURL = new java.net.URL(urlString);
            attachment.setURL(testURL);
            org.junit.Assert.assertEquals(testURL, attachment.getURL());
        }
    }

    @org.junit.Test
    public void testGetSetDisposition() {
        for (final java.lang.String validChar : testCharsValid) {
            attachment.setDisposition(validChar);
            org.junit.Assert.assertEquals(validChar, attachment.getDisposition());
        }
    }
}

