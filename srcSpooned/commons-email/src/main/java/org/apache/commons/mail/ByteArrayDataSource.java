package org.apache.commons.mail;


@java.lang.Deprecated
public class ByteArrayDataSource implements javax.activation.DataSource {
    public static final int BUFFER_SIZE = 512;

    private java.io.ByteArrayOutputStream baos;

    private final java.lang.String type;

    private java.lang.String name = "";

    public ByteArrayDataSource(final byte[] data ,final java.lang.String aType) throws java.io.IOException {
        this.type = aType;
        java.io.ByteArrayInputStream bis = null;
        try {
            bis = new java.io.ByteArrayInputStream(data);
            byteArrayDataSource(bis);
        } finally {
            if (bis != null) {
                bis.close();
            } 
        }
    }

    public ByteArrayDataSource(final java.io.InputStream aIs ,final java.lang.String aType) throws java.io.IOException {
        this.type = aType;
        byteArrayDataSource(aIs);
    }

    public ByteArrayDataSource(final java.lang.String data ,final java.lang.String aType) throws java.io.IOException {
        this.type = aType;
        try {
            baos = new java.io.ByteArrayOutputStream();
            baos.write(data.getBytes("iso-8859-1"));
            baos.flush();
            baos.close();
        } catch (final java.io.UnsupportedEncodingException uex) {
            return;
        } finally {
            if ((baos) != null) {
                baos.close();
            } 
        }
    }

    private void byteArrayDataSource(final java.io.InputStream aIs) throws java.io.IOException {
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream osWriter = null;
        try {
            int length = 0;
            final byte[] buffer = new byte[BUFFER_SIZE];
            bis = new java.io.BufferedInputStream(aIs);
            baos = new java.io.ByteArrayOutputStream();
            osWriter = new java.io.BufferedOutputStream(baos);
            while ((length = bis.read(buffer)) != (-1)) {
                osWriter.write(buffer, 0, length);
            }
            osWriter.flush();
            osWriter.close();
        } finally {
            if (bis != null) {
                bis.close();
            } 
            if ((baos) != null) {
                baos.close();
            } 
            if (osWriter != null) {
                osWriter.close();
            } 
        }
    }

    public java.lang.String getContentType() {
        return (type) == null ? "application/octet-stream" : type;
    }

    public java.io.InputStream getInputStream() throws java.io.IOException {
        if ((baos) == null) {
            return null;
        } 
        return new java.io.ByteArrayInputStream(baos.toByteArray());
    }

    public void setName(final java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.io.OutputStream getOutputStream() {
        baos = new java.io.ByteArrayOutputStream();
        return baos;
    }
}

