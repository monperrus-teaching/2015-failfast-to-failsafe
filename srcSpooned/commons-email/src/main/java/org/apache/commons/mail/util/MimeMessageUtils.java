package org.apache.commons.mail.util;


public final class MimeMessageUtils {
    private MimeMessageUtils() {
        super();
    }

    public static javax.mail.internet.MimeMessage createMimeMessage(final javax.mail.Session session, final byte[] source) throws java.io.IOException, javax.mail.MessagingException {
        java.io.ByteArrayInputStream is = null;
        try {
            is = new java.io.ByteArrayInputStream(source);
            return new javax.mail.internet.MimeMessage(session , is);
        } finally {
            if (is != null) {
                is.close();
            } 
        }
    }

    public static javax.mail.internet.MimeMessage createMimeMessage(final javax.mail.Session session, final java.io.File source) throws java.io.IOException, javax.mail.MessagingException {
        java.io.FileInputStream is = null;
        try {
            is = new java.io.FileInputStream(source);
            return org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, is);
        } finally {
            if (is != null) {
                is.close();
            } 
        }
    }

    public static javax.mail.internet.MimeMessage createMimeMessage(final javax.mail.Session session, final java.io.InputStream source) throws javax.mail.MessagingException {
        return new javax.mail.internet.MimeMessage(session , source);
    }

    public static javax.mail.internet.MimeMessage createMimeMessage(final javax.mail.Session session, final java.lang.String source) throws java.io.IOException, javax.mail.MessagingException {
        java.io.ByteArrayInputStream is = null;
        try {
            final byte[] byteSource = source.getBytes();
            is = new java.io.ByteArrayInputStream(byteSource);
            return org.apache.commons.mail.util.MimeMessageUtils.createMimeMessage(session, is);
        } finally {
            if (is != null) {
                is.close();
            } 
        }
    }

    public static void writeMimeMessage(final javax.mail.internet.MimeMessage mimeMessage, final java.io.File resultFile) throws java.io.IOException, javax.mail.MessagingException {
        java.io.FileOutputStream fos = null;
        try {
            if ((!(resultFile.getParentFile().exists())) && (!(resultFile.getParentFile().mkdirs()))) {
                throw new java.io.IOException(("Failed to create the following parent directories: " + (resultFile.getParentFile())));
            } 
            fos = new java.io.FileOutputStream(resultFile);
            mimeMessage.writeTo(fos);
            fos.flush();
            fos.close();
            fos = null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final java.lang.Exception e) {
                    e.printStackTrace();
                }
            } 
        }
    }
}

