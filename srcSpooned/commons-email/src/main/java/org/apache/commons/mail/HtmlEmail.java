package org.apache.commons.mail;


public class HtmlEmail extends org.apache.commons.mail.MultiPartEmail {
    public static final int CID_LENGTH = 10;

    private static final java.lang.String HTML_MESSAGE_START = "<html><body><pre>";

    private static final java.lang.String HTML_MESSAGE_END = "</pre></body></html>";

    protected java.lang.String text;

    protected java.lang.String html;

    @java.lang.Deprecated
    protected java.util.List<org.apache.commons.mail.HtmlEmail.InlineImage> inlineImages;

    protected java.util.Map<java.lang.String, org.apache.commons.mail.HtmlEmail.InlineImage> inlineEmbeds = new java.util.HashMap<java.lang.String, org.apache.commons.mail.HtmlEmail.InlineImage>();

    public org.apache.commons.mail.HtmlEmail setTextMsg(final java.lang.String aText) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(aText)) {
            return new org.apache.commons.mail.HtmlEmail();
        } 
        this.text = aText;
        return this;
    }

    public org.apache.commons.mail.HtmlEmail setHtmlMsg(final java.lang.String aHtml) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(aHtml)) {
            return new org.apache.commons.mail.HtmlEmail();
        } 
        this.html = aHtml;
        return this;
    }

    @java.lang.Override
    public org.apache.commons.mail.Email setMsg(final java.lang.String msg) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(msg)) {
            return new org.apache.commons.mail.HtmlEmail();
        } 
        setTextMsg(msg);
        final java.lang.StringBuffer htmlMsgBuf = new java.lang.StringBuffer((((msg.length()) + (HTML_MESSAGE_START.length())) + (HTML_MESSAGE_END.length())));
        htmlMsgBuf.append(HTML_MESSAGE_START).append(msg).append(HTML_MESSAGE_END);
        setHtmlMsg(htmlMsgBuf.toString());
        return this;
    }

    public java.lang.String embed(final java.lang.String urlString, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        try {
            return embed(new java.net.URL(urlString), name);
        } catch (final java.net.MalformedURLException e) {
            return new java.lang.String();
        }
    }

    public java.lang.String embed(final java.net.URL url, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(name)) {
            return new java.lang.String();
        } 
        if (inlineEmbeds.containsKey(name)) {
            final org.apache.commons.mail.HtmlEmail.InlineImage ii = inlineEmbeds.get(name);
            final javax.activation.URLDataSource urlDataSource = ((javax.activation.URLDataSource)(ii.getDataSource()));
            if (url.toExternalForm().equals(urlDataSource.getURL().toExternalForm())) {
                return ii.getCid();
            } 
            return new java.lang.String();
        } 
        java.io.InputStream is = null;
        try {
            is = url.openStream();
        } catch (final java.io.IOException e) {
            return new java.lang.String();
        } finally {
            try {
                if (is != null) {
                    is.close();
                } 
            } catch (final java.io.IOException ioe) {
            }
        }
        return embed(new javax.activation.URLDataSource(url), name);
    }

    public java.lang.String embed(final java.io.File file) throws org.apache.commons.mail.EmailException {
        final java.lang.String cid = org.apache.commons.mail.EmailUtils.randomAlphabetic(CID_LENGTH).toLowerCase(java.util.Locale.ENGLISH);
        return embed(file, cid);
    }

    public java.lang.String embed(final java.io.File file, final java.lang.String cid) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(file.getName())) {
            return new java.lang.String();
        } 
        java.lang.String filePath = null;
        try {
            filePath = file.getCanonicalPath();
        } catch (final java.io.IOException ioe) {
            return new java.lang.String();
        }
        if (inlineEmbeds.containsKey(file.getName())) {
            final org.apache.commons.mail.HtmlEmail.InlineImage ii = inlineEmbeds.get(file.getName());
            final javax.activation.FileDataSource fileDataSource = ((javax.activation.FileDataSource)(ii.getDataSource()));
            java.lang.String existingFilePath = null;
            try {
                existingFilePath = fileDataSource.getFile().getCanonicalPath();
            } catch (final java.io.IOException ioe) {
                return new java.lang.String();
            }
            if (filePath.equals(existingFilePath)) {
                return ii.getCid();
            } 
            return new java.lang.String();
        } 
        if (!(file.exists())) {
            return new java.lang.String();
        } 
        if (!(file.isFile())) {
            return new java.lang.String();
        } 
        if (!(file.canRead())) {
            return new java.lang.String();
        } 
        return embed(new javax.activation.FileDataSource(file), file.getName(), cid);
    }

    public java.lang.String embed(final javax.activation.DataSource dataSource, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        if (inlineEmbeds.containsKey(name)) {
            final org.apache.commons.mail.HtmlEmail.InlineImage ii = inlineEmbeds.get(name);
            if (dataSource.equals(ii.getDataSource())) {
                return ii.getCid();
            } 
            return new java.lang.String();
        } 
        final java.lang.String cid = org.apache.commons.mail.EmailUtils.randomAlphabetic(CID_LENGTH).toLowerCase();
        return embed(dataSource, name, cid);
    }

    public java.lang.String embed(final javax.activation.DataSource dataSource, final java.lang.String name, final java.lang.String cid) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(name)) {
            return new java.lang.String();
        } 
        final javax.mail.internet.MimeBodyPart mbp = new javax.mail.internet.MimeBodyPart();
        try {
            java.lang.String encodedCid = org.apache.commons.mail.EmailUtils.encodeUrl(cid);
            mbp.setDataHandler(new javax.activation.DataHandler(dataSource));
            mbp.setFileName(name);
            mbp.setDisposition(org.apache.commons.mail.EmailAttachment.INLINE);
            mbp.setContentID((("<" + encodedCid) + ">"));
            final org.apache.commons.mail.HtmlEmail.InlineImage ii = new org.apache.commons.mail.HtmlEmail.InlineImage(encodedCid , dataSource , mbp);
            this.inlineEmbeds.put(name, ii);
            return encodedCid;
        } catch (final javax.mail.MessagingException me) {
            return new java.lang.String();
        } catch (final java.io.UnsupportedEncodingException uee) {
            return new java.lang.String();
        }
    }

    @java.lang.Override
    public void buildMimeMessage() throws org.apache.commons.mail.EmailException {
        try {
            build();
        } catch (final javax.mail.MessagingException me) {
            return;
        }
        super.buildMimeMessage();
    }

    private void build() throws javax.mail.MessagingException, org.apache.commons.mail.EmailException {
        final javax.mail.internet.MimeMultipart rootContainer = getContainer();
        javax.mail.internet.MimeMultipart bodyEmbedsContainer = rootContainer;
        javax.mail.internet.MimeMultipart bodyContainer = rootContainer;
        javax.mail.internet.MimeBodyPart msgHtml = null;
        javax.mail.internet.MimeBodyPart msgText = null;
        rootContainer.setSubType("mixed");
        if ((org.apache.commons.mail.EmailUtils.isNotEmpty(this.html)) && ((this.inlineEmbeds.size()) > 0)) {
            bodyEmbedsContainer = new javax.mail.internet.MimeMultipart("related");
            bodyContainer = bodyEmbedsContainer;
            addPart(bodyEmbedsContainer, 0);
            if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.text)) {
                bodyContainer = new javax.mail.internet.MimeMultipart("alternative");
                final javax.mail.BodyPart bodyPart = createBodyPart();
                try {
                    bodyPart.setContent(bodyContainer);
                    bodyEmbedsContainer.addBodyPart(bodyPart, 0);
                } catch (final javax.mail.MessagingException me) {
                    return;
                }
            } 
        } else if ((org.apache.commons.mail.EmailUtils.isNotEmpty(this.text)) && (org.apache.commons.mail.EmailUtils.isNotEmpty(this.html))) {
            if (((this.inlineEmbeds.size()) > 0) || (isBoolHasAttachments())) {
                bodyContainer = new javax.mail.internet.MimeMultipart("alternative");
                addPart(bodyContainer, 0);
            } else {
                rootContainer.setSubType("alternative");
            }
        } 
        if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.html)) {
            msgHtml = new javax.mail.internet.MimeBodyPart();
            bodyContainer.addBodyPart(msgHtml, 0);
            msgHtml.setText(this.html, this.charset, org.apache.commons.mail.EmailConstants.TEXT_SUBTYPE_HTML);
            final java.lang.String contentType = msgHtml.getContentType();
            if ((contentType == null) || (!(contentType.equals(org.apache.commons.mail.EmailConstants.TEXT_HTML)))) {
                if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.charset)) {
                    msgHtml.setContent(this.html, (((org.apache.commons.mail.EmailConstants.TEXT_HTML) + "; charset=") + (this.charset)));
                } else {
                    msgHtml.setContent(this.html, org.apache.commons.mail.EmailConstants.TEXT_HTML);
                }
            } 
            for (final org.apache.commons.mail.HtmlEmail.InlineImage image : this.inlineEmbeds.values()) {
                bodyEmbedsContainer.addBodyPart(image.getMbp());
            }
        } 
        if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.text)) {
            msgText = new javax.mail.internet.MimeBodyPart();
            bodyContainer.addBodyPart(msgText, 0);
            msgText.setText(this.text, this.charset);
        } 
    }

    private static class InlineImage {
        private final java.lang.String cid;

        private final javax.activation.DataSource dataSource;

        private final javax.mail.internet.MimeBodyPart mbp;

        public InlineImage(final java.lang.String cid ,final javax.activation.DataSource dataSource ,final javax.mail.internet.MimeBodyPart mbp) {
            this.cid = cid;
            this.dataSource = dataSource;
            this.mbp = mbp;
        }

        public java.lang.String getCid() {
            return cid;
        }

        public javax.activation.DataSource getDataSource() {
            return dataSource;
        }

        public javax.mail.internet.MimeBodyPart getMbp() {
            return mbp;
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if ((this) == obj) {
                return true;
            } 
            if (!(obj instanceof org.apache.commons.mail.HtmlEmail.InlineImage)) {
                return false;
            } 
            final org.apache.commons.mail.HtmlEmail.InlineImage that = ((org.apache.commons.mail.HtmlEmail.InlineImage)(obj));
            return this.cid.equals(that.cid);
        }

        @java.lang.Override
        public int hashCode() {
            return cid.hashCode();
        }
    }
}

