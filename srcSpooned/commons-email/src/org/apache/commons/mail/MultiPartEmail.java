package org.apache.commons.mail;


public class MultiPartEmail extends org.apache.commons.mail.Email {
    private javax.mail.internet.MimeMultipart container;

    private javax.mail.BodyPart primaryBodyPart;

    private java.lang.String subType;

    private boolean initialized;

    private boolean boolHasAttachments;

    public void setSubType(final java.lang.String aSubType) {
        this.subType = aSubType;
    }

    public java.lang.String getSubType() {
        return subType;
    }

    public org.apache.commons.mail.Email addPart(final java.lang.String partContent, final java.lang.String partContentType) throws org.apache.commons.mail.EmailException {
        final javax.mail.BodyPart bodyPart = createBodyPart();
        try {
            bodyPart.setContent(partContent, partContentType);
            getContainer().addBodyPart(bodyPart);
        } catch (final javax.mail.MessagingException me) {
            throw new org.apache.commons.mail.EmailException(me);
        }
        return this;
    }

    public org.apache.commons.mail.Email addPart(final javax.mail.internet.MimeMultipart multipart) throws org.apache.commons.mail.EmailException {
        try {
            return addPart(multipart, getContainer().getCount());
        } catch (final javax.mail.MessagingException me) {
            throw new org.apache.commons.mail.EmailException(me);
        }
    }

    public org.apache.commons.mail.Email addPart(final javax.mail.internet.MimeMultipart multipart, final int index) throws org.apache.commons.mail.EmailException {
        final javax.mail.BodyPart bodyPart = createBodyPart();
        try {
            bodyPart.setContent(multipart);
            getContainer().addBodyPart(bodyPart, index);
        } catch (final javax.mail.MessagingException me) {
            throw new org.apache.commons.mail.EmailException(me);
        }
        return this;
    }

    protected void init() {
        if (initialized) {
            throw new java.lang.IllegalStateException("Already initialized");
        } 
        container = createMimeMultipart();
        super.setContent(container);
        initialized = true;
    }

    @java.lang.Override
    public org.apache.commons.mail.Email setMsg(final java.lang.String msg) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(msg)) {
            throw new org.apache.commons.mail.EmailException("Invalid message supplied");
        } 
        try {
            final javax.mail.BodyPart primary = getPrimaryBodyPart();
            if ((primary instanceof javax.mail.internet.MimePart) && (org.apache.commons.mail.EmailUtils.isNotEmpty(charset))) {
                ((javax.mail.internet.MimePart)(primary)).setText(msg, charset);
            } else {
                primary.setText(msg);
            }
        } catch (final javax.mail.MessagingException me) {
            throw new org.apache.commons.mail.EmailException(me);
        }
        return this;
    }

    @java.lang.Override
    public void buildMimeMessage() throws org.apache.commons.mail.EmailException {
        try {
            if ((primaryBodyPart) != null) {
                final javax.mail.BodyPart body = getPrimaryBodyPart();
                try {
                    body.getContent();
                } catch (final java.io.IOException e) {
                }
            } 
            if ((subType) != null) {
                getContainer().setSubType(subType);
            } 
            super.buildMimeMessage();
        } catch (final javax.mail.MessagingException me) {
            throw new org.apache.commons.mail.EmailException(me);
        }
    }

    public org.apache.commons.mail.MultiPartEmail attach(final java.io.File file) throws org.apache.commons.mail.EmailException {
        final java.lang.String fileName = file.getAbsolutePath();
        try {
            if (!(file.exists())) {
                throw new java.io.IOException((("\"" + fileName) + "\" does not exist"));
            } 
            final javax.activation.FileDataSource fds = new javax.activation.FileDataSource(file);
            return attach(fds, file.getName(), null, org.apache.commons.mail.EmailAttachment.ATTACHMENT);
        } catch (final java.io.IOException e) {
            throw new org.apache.commons.mail.EmailException((("Cannot attach file \"" + fileName) + "\"") , e);
        }
    }

    public org.apache.commons.mail.MultiPartEmail attach(final org.apache.commons.mail.EmailAttachment attachment) throws org.apache.commons.mail.EmailException {
        org.apache.commons.mail.MultiPartEmail result = null;
        if (attachment == null) {
            throw new org.apache.commons.mail.EmailException("Invalid attachment supplied");
        } 
        final java.net.URL url = attachment.getURL();
        if (url == null) {
            java.lang.String fileName = null;
            try {
                fileName = attachment.getPath();
                final java.io.File file = new java.io.File(fileName);
                if (!(file.exists())) {
                    throw new java.io.IOException((("\"" + fileName) + "\" does not exist"));
                } 
                result = attach(new javax.activation.FileDataSource(file), attachment.getName(), attachment.getDescription(), attachment.getDisposition());
            } catch (final java.io.IOException e) {
                throw new org.apache.commons.mail.EmailException((("Cannot attach file \"" + fileName) + "\"") , e);
            }
        } else {
            result = attach(url, attachment.getName(), attachment.getDescription(), attachment.getDisposition());
        }
        return result;
    }

    public org.apache.commons.mail.MultiPartEmail attach(final java.net.URL url, final java.lang.String name, final java.lang.String description) throws org.apache.commons.mail.EmailException {
        return attach(url, name, description, org.apache.commons.mail.EmailAttachment.ATTACHMENT);
    }

    public org.apache.commons.mail.MultiPartEmail attach(final java.net.URL url, final java.lang.String name, final java.lang.String description, final java.lang.String disposition) throws org.apache.commons.mail.EmailException {
        try {
            final java.io.InputStream is = url.openStream();
            is.close();
        } catch (final java.io.IOException e) {
            throw new org.apache.commons.mail.EmailException(("Invalid URL set:" + url) , e);
        }
        return attach(new javax.activation.URLDataSource(url), name, description, disposition);
    }

    public org.apache.commons.mail.MultiPartEmail attach(final javax.activation.DataSource ds, final java.lang.String name, final java.lang.String description) throws org.apache.commons.mail.EmailException {
        try {
            final java.io.InputStream is = ds != null ? ds.getInputStream() : null;
            if (is != null) {
                is.close();
            } 
            if (is == null) {
                throw new org.apache.commons.mail.EmailException("Invalid Datasource");
            } 
        } catch (final java.io.IOException e) {
            throw new org.apache.commons.mail.EmailException("Invalid Datasource" , e);
        }
        return attach(ds, name, description, org.apache.commons.mail.EmailAttachment.ATTACHMENT);
    }

    public org.apache.commons.mail.MultiPartEmail attach(final javax.activation.DataSource ds, java.lang.String name, final java.lang.String description, final java.lang.String disposition) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(name)) {
            name = ds.getName();
        } 
        final javax.mail.BodyPart bodyPart = createBodyPart();
        try {
            bodyPart.setDisposition(disposition);
            bodyPart.setFileName(javax.mail.internet.MimeUtility.encodeText(name));
            bodyPart.setDescription(description);
            bodyPart.setDataHandler(new javax.activation.DataHandler(ds));
            getContainer().addBodyPart(bodyPart);
        } catch (final java.io.UnsupportedEncodingException uee) {
            throw new org.apache.commons.mail.EmailException(uee);
        } catch (final javax.mail.MessagingException me) {
            throw new org.apache.commons.mail.EmailException(me);
        }
        setBoolHasAttachments(true);
        return this;
    }

    protected javax.mail.BodyPart getPrimaryBodyPart() throws javax.mail.MessagingException {
        if (!(initialized)) {
            init();
        } 
        if ((this.primaryBodyPart) == null) {
            primaryBodyPart = createBodyPart();
            getContainer().addBodyPart(primaryBodyPart, 0);
        } 
        return primaryBodyPart;
    }

    protected javax.mail.internet.MimeMultipart getContainer() {
        if (!(initialized)) {
            init();
        } 
        return container;
    }

    protected javax.mail.BodyPart createBodyPart() {
        return new javax.mail.internet.MimeBodyPart();
    }

    protected javax.mail.internet.MimeMultipart createMimeMultipart() {
        return new javax.mail.internet.MimeMultipart();
    }

    public boolean isBoolHasAttachments() {
        return boolHasAttachments;
    }

    public void setBoolHasAttachments(final boolean b) {
        boolHasAttachments = b;
    }

    protected boolean isInitialized() {
        return initialized;
    }

    protected void setInitialized(final boolean b) {
        initialized = b;
    }
}

