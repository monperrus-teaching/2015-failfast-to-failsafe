package org.apache.commons.mail.util;


public class MimeMessageParser {
    private final javax.mail.internet.MimeMessage mimeMessage;

    private java.lang.String plainContent;

    private java.lang.String htmlContent;

    private final java.util.List<javax.activation.DataSource> attachmentList;

    private final java.util.Map<java.lang.String, javax.activation.DataSource> cidMap;

    private boolean isMultiPart;

    public MimeMessageParser(final javax.mail.internet.MimeMessage message) {
        attachmentList = new java.util.ArrayList<javax.activation.DataSource>();
        cidMap = new java.util.HashMap<java.lang.String, javax.activation.DataSource>();
        this.mimeMessage = message;
        this.isMultiPart = false;
    }

    public org.apache.commons.mail.util.MimeMessageParser parse() throws java.lang.Exception {
        parse(null, mimeMessage);
        return this;
    }

    public java.util.List<javax.mail.Address> getTo() throws java.lang.Exception {
        final javax.mail.Address[] recipients = this.mimeMessage.getRecipients(javax.mail.Message.RecipientType.TO);
        return recipients != null ? java.util.Arrays.asList(recipients) : new java.util.ArrayList<javax.mail.Address>();
    }

    public java.util.List<javax.mail.Address> getCc() throws java.lang.Exception {
        final javax.mail.Address[] recipients = this.mimeMessage.getRecipients(javax.mail.Message.RecipientType.CC);
        return recipients != null ? java.util.Arrays.asList(recipients) : new java.util.ArrayList<javax.mail.Address>();
    }

    public java.util.List<javax.mail.Address> getBcc() throws java.lang.Exception {
        final javax.mail.Address[] recipients = this.mimeMessage.getRecipients(javax.mail.Message.RecipientType.BCC);
        return recipients != null ? java.util.Arrays.asList(recipients) : new java.util.ArrayList<javax.mail.Address>();
    }

    public java.lang.String getFrom() throws java.lang.Exception {
        final javax.mail.Address[] addresses = this.mimeMessage.getFrom();
        if ((addresses == null) || ((addresses.length) == 0)) {
            return null;
        } 
        return ((javax.mail.internet.InternetAddress)(addresses[0])).getAddress();
    }

    public java.lang.String getReplyTo() throws java.lang.Exception {
        final javax.mail.Address[] addresses = this.mimeMessage.getReplyTo();
        if ((addresses == null) || ((addresses.length) == 0)) {
            return null;
        } 
        return ((javax.mail.internet.InternetAddress)(addresses[0])).getAddress();
    }

    public java.lang.String getSubject() throws java.lang.Exception {
        return this.mimeMessage.getSubject();
    }

    protected void parse(final javax.mail.Multipart parent, final javax.mail.internet.MimePart part) throws java.io.IOException, javax.mail.MessagingException {
        if (((isMimeType(part, "text/plain")) && ((plainContent) == null)) && (!(javax.mail.internet.MimePart.ATTACHMENT.equalsIgnoreCase(part.getDisposition())))) {
            plainContent = ((java.lang.String)(part.getContent()));
        } else {
            if (((isMimeType(part, "text/html")) && ((htmlContent) == null)) && (!(javax.mail.internet.MimePart.ATTACHMENT.equalsIgnoreCase(part.getDisposition())))) {
                htmlContent = ((java.lang.String)(part.getContent()));
            } else {
                if (isMimeType(part, "multipart/*")) {
                    this.isMultiPart = true;
                    final javax.mail.Multipart mp = ((javax.mail.Multipart)(part.getContent()));
                    final int count = mp.getCount();
                    for (int i = 0 ; i < count ; i++) {
                        parse(mp, ((javax.mail.internet.MimeBodyPart)(mp.getBodyPart(i))));
                    }
                } else {
                    final java.lang.String cid = stripContentId(part.getContentID());
                    final javax.activation.DataSource ds = createDataSource(parent, part);
                    if (cid != null) {
                        this.cidMap.put(cid, ds);
                    } 
                    this.attachmentList.add(ds);
                }
            }
        }
    }

    private java.lang.String stripContentId(final java.lang.String contentId) {
        if (contentId == null) {
            return null;
        } 
        return contentId.trim().replaceAll("[\\<\\>]", "");
    }

    private boolean isMimeType(final javax.mail.internet.MimePart part, final java.lang.String mimeType) throws java.io.IOException, javax.mail.MessagingException {
        try {
            final javax.mail.internet.ContentType ct = new javax.mail.internet.ContentType(part.getDataHandler().getContentType());
            return ct.match(mimeType);
        } catch (final javax.mail.internet.ParseException ex) {
            return part.getContentType().equalsIgnoreCase(mimeType);
        }
    }

    protected javax.activation.DataSource createDataSource(final javax.mail.Multipart parent, final javax.mail.internet.MimePart part) throws java.io.IOException, javax.mail.MessagingException {
        final javax.activation.DataHandler dataHandler = part.getDataHandler();
        final javax.activation.DataSource dataSource = dataHandler.getDataSource();
        final java.lang.String contentType = getBaseMimeType(dataSource.getContentType());
        final byte[] content = getContent(dataSource.getInputStream());
        final javax.mail.util.ByteArrayDataSource result = new javax.mail.util.ByteArrayDataSource(content , contentType);
        final java.lang.String dataSourceName = getDataSourceName(part, dataSource);
        result.setName(dataSourceName);
        return result;
    }

    public javax.mail.internet.MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public boolean isMultipart() {
        return isMultiPart;
    }

    public java.lang.String getPlainContent() {
        return plainContent;
    }

    public java.util.List<javax.activation.DataSource> getAttachmentList() {
        return attachmentList;
    }

    public java.util.Collection<java.lang.String> getContentIds() {
        return java.util.Collections.unmodifiableSet(cidMap.keySet());
    }

    public java.lang.String getHtmlContent() {
        return htmlContent;
    }

    public boolean hasPlainContent() {
        return (this.plainContent) != null;
    }

    public boolean hasHtmlContent() {
        return (this.htmlContent) != null;
    }

    public boolean hasAttachments() {
        return (this.attachmentList.size()) > 0;
    }

    public javax.activation.DataSource findAttachmentByName(final java.lang.String name) {
        javax.activation.DataSource dataSource;
        for (int i = 0 ; i < (getAttachmentList().size()) ; i++) {
            dataSource = getAttachmentList().get(i);
            if (name.equalsIgnoreCase(dataSource.getName())) {
                return dataSource;
            } 
        }
        return null;
    }

    public javax.activation.DataSource findAttachmentByCid(final java.lang.String cid) {
        final javax.activation.DataSource dataSource = cidMap.get(cid);
        return dataSource;
    }

    protected java.lang.String getDataSourceName(final javax.mail.Part part, final javax.activation.DataSource dataSource) throws java.io.UnsupportedEncodingException, javax.mail.MessagingException {
        java.lang.String result = dataSource.getName();
        if ((result == null) || ((result.length()) == 0)) {
            result = part.getFileName();
        } 
        if ((result != null) && ((result.length()) > 0)) {
            result = javax.mail.internet.MimeUtility.decodeText(result);
        } else {
            result = null;
        }
        return result;
    }

    private byte[] getContent(final java.io.InputStream is) throws java.io.IOException {
        int ch;
        byte[] result;
        final java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
        final java.io.BufferedInputStream isReader = new java.io.BufferedInputStream(is);
        final java.io.BufferedOutputStream osWriter = new java.io.BufferedOutputStream(os);
        while ((ch = isReader.read()) != (-1)) {
            osWriter.write(ch);
        }
        osWriter.flush();
        result = os.toByteArray();
        osWriter.close();
        return result;
    }

    private java.lang.String getBaseMimeType(final java.lang.String fullMimeType) {
        final int pos = fullMimeType.indexOf(';');
        if (pos >= 0) {
            return fullMimeType.substring(0, pos);
        } 
        return fullMimeType;
    }
}

