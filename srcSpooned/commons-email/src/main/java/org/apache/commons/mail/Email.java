package org.apache.commons.mail;


public abstract class Email {
    @java.lang.Deprecated
    public static final java.lang.String SENDER_EMAIL = org.apache.commons.mail.EmailConstants.SENDER_EMAIL;

    @java.lang.Deprecated
    public static final java.lang.String SENDER_NAME = org.apache.commons.mail.EmailConstants.SENDER_NAME;

    @java.lang.Deprecated
    public static final java.lang.String RECEIVER_EMAIL = org.apache.commons.mail.EmailConstants.RECEIVER_EMAIL;

    @java.lang.Deprecated
    public static final java.lang.String RECEIVER_NAME = org.apache.commons.mail.EmailConstants.RECEIVER_NAME;

    @java.lang.Deprecated
    public static final java.lang.String EMAIL_SUBJECT = org.apache.commons.mail.EmailConstants.EMAIL_SUBJECT;

    @java.lang.Deprecated
    public static final java.lang.String EMAIL_BODY = org.apache.commons.mail.EmailConstants.EMAIL_BODY;

    @java.lang.Deprecated
    public static final java.lang.String CONTENT_TYPE = org.apache.commons.mail.EmailConstants.CONTENT_TYPE;

    @java.lang.Deprecated
    public static final java.lang.String ATTACHMENTS = org.apache.commons.mail.EmailConstants.ATTACHMENTS;

    @java.lang.Deprecated
    public static final java.lang.String FILE_SERVER = org.apache.commons.mail.EmailConstants.FILE_SERVER;

    @java.lang.Deprecated
    public static final java.lang.String KOI8_R = org.apache.commons.mail.EmailConstants.KOI8_R;

    @java.lang.Deprecated
    public static final java.lang.String ISO_8859_1 = org.apache.commons.mail.EmailConstants.ISO_8859_1;

    @java.lang.Deprecated
    public static final java.lang.String US_ASCII = org.apache.commons.mail.EmailConstants.US_ASCII;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_DEBUG = org.apache.commons.mail.EmailConstants.MAIL_DEBUG;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_HOST = org.apache.commons.mail.EmailConstants.MAIL_HOST;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_PORT = org.apache.commons.mail.EmailConstants.MAIL_PORT;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_FROM = org.apache.commons.mail.EmailConstants.MAIL_SMTP_FROM;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_AUTH = org.apache.commons.mail.EmailConstants.MAIL_SMTP_AUTH;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_USER = org.apache.commons.mail.EmailConstants.MAIL_SMTP_USER;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_PASSWORD = org.apache.commons.mail.EmailConstants.MAIL_SMTP_PASSWORD;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_TRANSPORT_PROTOCOL = org.apache.commons.mail.EmailConstants.MAIL_TRANSPORT_PROTOCOL;

    @java.lang.Deprecated
    public static final java.lang.String SMTP = org.apache.commons.mail.EmailConstants.SMTP;

    @java.lang.Deprecated
    public static final java.lang.String TEXT_HTML = org.apache.commons.mail.EmailConstants.TEXT_HTML;

    @java.lang.Deprecated
    public static final java.lang.String TEXT_PLAIN = org.apache.commons.mail.EmailConstants.TEXT_PLAIN;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_TRANSPORT_TLS = org.apache.commons.mail.EmailConstants.MAIL_TRANSPORT_TLS;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = org.apache.commons.mail.EmailConstants.MAIL_SMTP_SOCKET_FACTORY_FALLBACK;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_SOCKET_FACTORY_CLASS = org.apache.commons.mail.EmailConstants.MAIL_SMTP_SOCKET_FACTORY_CLASS;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_SOCKET_FACTORY_PORT = org.apache.commons.mail.EmailConstants.MAIL_SMTP_SOCKET_FACTORY_PORT;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_CONNECTIONTIMEOUT = org.apache.commons.mail.EmailConstants.MAIL_SMTP_CONNECTIONTIMEOUT;

    @java.lang.Deprecated
    public static final java.lang.String MAIL_SMTP_TIMEOUT = org.apache.commons.mail.EmailConstants.MAIL_SMTP_TIMEOUT;

    protected javax.mail.internet.MimeMessage message;

    protected java.lang.String charset;

    protected javax.mail.internet.InternetAddress fromAddress;

    protected java.lang.String subject;

    protected javax.mail.internet.MimeMultipart emailBody;

    protected java.lang.Object content;

    protected java.lang.String contentType;

    protected boolean debug;

    protected java.util.Date sentDate;

    protected javax.mail.Authenticator authenticator;

    protected java.lang.String hostName;

    protected java.lang.String smtpPort = "25";

    protected java.lang.String sslSmtpPort = "465";

    protected java.util.List<javax.mail.internet.InternetAddress> toList = new java.util.ArrayList<javax.mail.internet.InternetAddress>();

    protected java.util.List<javax.mail.internet.InternetAddress> ccList = new java.util.ArrayList<javax.mail.internet.InternetAddress>();

    protected java.util.List<javax.mail.internet.InternetAddress> bccList = new java.util.ArrayList<javax.mail.internet.InternetAddress>();

    protected java.util.List<javax.mail.internet.InternetAddress> replyList = new java.util.ArrayList<javax.mail.internet.InternetAddress>();

    protected java.lang.String bounceAddress;

    protected java.util.Map<java.lang.String, java.lang.String> headers = new java.util.HashMap<java.lang.String, java.lang.String>();

    protected boolean popBeforeSmtp;

    protected java.lang.String popHost;

    protected java.lang.String popUsername;

    protected java.lang.String popPassword;

    @java.lang.Deprecated
    protected boolean tls;

    @java.lang.Deprecated
    protected boolean ssl;

    protected int socketTimeout = org.apache.commons.mail.EmailConstants.SOCKET_TIMEOUT_MS;

    protected int socketConnectionTimeout = org.apache.commons.mail.EmailConstants.SOCKET_TIMEOUT_MS;

    private boolean startTlsEnabled;

    private boolean startTlsRequired;

    private boolean sslOnConnect;

    private boolean sslCheckServerIdentity;

    private boolean sendPartial;

    private javax.mail.Session session;

    public void setDebug(final boolean d) {
        this.debug = d;
    }

    public void setAuthentication(final java.lang.String userName, final java.lang.String password) {
        setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(userName , password));
    }

    public void setAuthenticator(final javax.mail.Authenticator newAuthenticator) {
        this.authenticator = newAuthenticator;
    }

    public void setCharset(final java.lang.String newCharset) {
        final java.nio.charset.Charset set = java.nio.charset.Charset.forName(newCharset);
        this.charset = set.name();
    }

    public void setContent(final javax.mail.internet.MimeMultipart aMimeMultipart) {
        this.emailBody = aMimeMultipart;
    }

    public void setContent(final java.lang.Object aObject, final java.lang.String aContentType) {
        this.content = aObject;
        updateContentType(aContentType);
    }

    public void updateContentType(final java.lang.String aContentType) {
        if (org.apache.commons.mail.EmailUtils.isEmpty(aContentType)) {
            this.contentType = null;
        } else {
            this.contentType = aContentType;
            final java.lang.String strMarker = "; charset=";
            int charsetPos = aContentType.toLowerCase().indexOf(strMarker);
            if (charsetPos != (-1)) {
                charsetPos += strMarker.length();
                final int intCharsetEnd = aContentType.toLowerCase().indexOf(" ", charsetPos);
                if (intCharsetEnd != (-1)) {
                    this.charset = aContentType.substring(charsetPos, intCharsetEnd);
                } else {
                    this.charset = aContentType.substring(charsetPos);
                }
            } else {
                if ((this.contentType.startsWith("text/")) && (org.apache.commons.mail.EmailUtils.isNotEmpty(this.charset))) {
                    final java.lang.StringBuffer contentTypeBuf = new java.lang.StringBuffer(this.contentType);
                    contentTypeBuf.append(strMarker);
                    contentTypeBuf.append(this.charset);
                    this.contentType = contentTypeBuf.toString();
                } 
            }
        }
    }

    public void setHostName(final java.lang.String aHostName) {
        checkSessionAlreadyInitialized();
        this.hostName = aHostName;
    }

    @java.lang.Deprecated
    public void setTLS(final boolean withTLS) {
        setStartTLSEnabled(withTLS);
    }

    public org.apache.commons.mail.Email setStartTLSEnabled(final boolean startTlsEnabled) {
        checkSessionAlreadyInitialized();
        this.startTlsEnabled = startTlsEnabled;
        this.tls = startTlsEnabled;
        return this;
    }

    public org.apache.commons.mail.Email setStartTLSRequired(final boolean startTlsRequired) {
        checkSessionAlreadyInitialized();
        this.startTlsRequired = startTlsRequired;
        return this;
    }

    public void setSmtpPort(final int aPortNumber) {
        checkSessionAlreadyInitialized();
        if (aPortNumber < 1) {
            return;
        } 
        this.smtpPort = java.lang.Integer.toString(aPortNumber);
    }

    public void setMailSession(final javax.mail.Session aSession) {
        org.apache.commons.mail.EmailUtils.notNull(aSession, "no mail session supplied");
        final java.util.Properties sessionProperties = aSession.getProperties();
        final java.lang.String auth = sessionProperties.getProperty(MAIL_SMTP_AUTH);
        if ("true".equalsIgnoreCase(auth)) {
            final java.lang.String userName = sessionProperties.getProperty(MAIL_SMTP_USER);
            final java.lang.String password = sessionProperties.getProperty(MAIL_SMTP_PASSWORD);
            if ((org.apache.commons.mail.EmailUtils.isNotEmpty(userName)) && (org.apache.commons.mail.EmailUtils.isNotEmpty(password))) {
                this.authenticator = new org.apache.commons.mail.DefaultAuthenticator(userName , password);
                this.session = javax.mail.Session.getInstance(sessionProperties, this.authenticator);
            } else {
                this.session = aSession;
            }
        } else {
            this.session = aSession;
        }
    }

    public void setMailSessionFromJNDI(final java.lang.String jndiName) throws javax.naming.NamingException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(jndiName)) {
            return;
        } 
        javax.naming.Context ctx = null;
        if (jndiName.startsWith("java:")) {
            ctx = new javax.naming.InitialContext();
        } else {
            ctx = ((javax.naming.Context)(new javax.naming.InitialContext().lookup("java:comp/env")));
        }
        setMailSession(((javax.mail.Session)(ctx.lookup(jndiName))));
    }

    public javax.mail.Session getMailSession() throws org.apache.commons.mail.EmailException {
        if ((this.session) == null) {
            final java.util.Properties properties = new java.util.Properties(java.lang.System.getProperties());
            properties.setProperty(MAIL_TRANSPORT_PROTOCOL, SMTP);
            if (org.apache.commons.mail.EmailUtils.isEmpty(this.hostName)) {
                this.hostName = properties.getProperty(MAIL_HOST);
            } 
            if (org.apache.commons.mail.EmailUtils.isEmpty(this.hostName)) {
                return null;
            } 
            properties.setProperty(MAIL_PORT, this.smtpPort);
            properties.setProperty(MAIL_HOST, this.hostName);
            properties.setProperty(MAIL_DEBUG, java.lang.String.valueOf(this.debug));
            properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_TRANSPORT_STARTTLS_ENABLE, (isStartTLSEnabled() ? "true" : "false"));
            properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_TRANSPORT_STARTTLS_REQUIRED, (isStartTLSRequired() ? "true" : "false"));
            properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_SMTP_SEND_PARTIAL, (isSendPartial() ? "true" : "false"));
            properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_SMTPS_SEND_PARTIAL, (isSendPartial() ? "true" : "false"));
            if ((this.authenticator) != null) {
                properties.setProperty(MAIL_SMTP_AUTH, "true");
            } 
            if (isSSLOnConnect()) {
                properties.setProperty(MAIL_PORT, this.sslSmtpPort);
                properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_PORT, this.sslSmtpPort);
                properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
                properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, "false");
            } 
            if (((isSSLOnConnect()) || (isStartTLSEnabled())) && (isSSLCheckServerIdentity())) {
                properties.setProperty(org.apache.commons.mail.EmailConstants.MAIL_SMTP_SSL_CHECKSERVERIDENTITY, "true");
            } 
            if ((this.bounceAddress) != null) {
                properties.setProperty(MAIL_SMTP_FROM, this.bounceAddress);
            } 
            if ((this.socketTimeout) > 0) {
                properties.setProperty(MAIL_SMTP_TIMEOUT, java.lang.Integer.toString(this.socketTimeout));
            } 
            if ((this.socketConnectionTimeout) > 0) {
                properties.setProperty(MAIL_SMTP_CONNECTIONTIMEOUT, java.lang.Integer.toString(this.socketConnectionTimeout));
            } 
            this.session = javax.mail.Session.getInstance(properties, this.authenticator);
        } 
        return this.session;
    }

    public org.apache.commons.mail.Email setFrom(final java.lang.String email) throws org.apache.commons.mail.EmailException {
        return setFrom(email, null);
    }

    public org.apache.commons.mail.Email setFrom(final java.lang.String email, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        return setFrom(email, name, this.charset);
    }

    public org.apache.commons.mail.Email setFrom(final java.lang.String email, final java.lang.String name, final java.lang.String charset) throws org.apache.commons.mail.EmailException {
        this.fromAddress = createInternetAddress(email, name, charset);
        return this;
    }

    public org.apache.commons.mail.Email addTo(final java.lang.String email) throws org.apache.commons.mail.EmailException {
        return addTo(email, null);
    }

    public org.apache.commons.mail.Email addTo(final java.lang.String... emails) throws org.apache.commons.mail.EmailException {
        if ((emails == null) || ((emails.length) == 0)) {
            return null;
        } 
        for (final java.lang.String email : emails) {
            addTo(email, null);
        }
        return this;
    }

    public org.apache.commons.mail.Email addTo(final java.lang.String email, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        return addTo(email, name, this.charset);
    }

    public org.apache.commons.mail.Email addTo(final java.lang.String email, final java.lang.String name, final java.lang.String charset) throws org.apache.commons.mail.EmailException {
        this.toList.add(createInternetAddress(email, name, charset));
        return this;
    }

    public org.apache.commons.mail.Email setTo(final java.util.Collection<javax.mail.internet.InternetAddress> aCollection) throws org.apache.commons.mail.EmailException {
        if ((aCollection == null) || (aCollection.isEmpty())) {
            return null;
        } 
        this.toList = new java.util.ArrayList<javax.mail.internet.InternetAddress>(aCollection);
        return this;
    }

    public org.apache.commons.mail.Email addCc(final java.lang.String email) throws org.apache.commons.mail.EmailException {
        return addCc(email, null);
    }

    public org.apache.commons.mail.Email addCc(final java.lang.String... emails) throws org.apache.commons.mail.EmailException {
        if ((emails == null) || ((emails.length) == 0)) {
            return null;
        } 
        for (final java.lang.String email : emails) {
            addCc(email, null);
        }
        return this;
    }

    public org.apache.commons.mail.Email addCc(final java.lang.String email, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        return addCc(email, name, this.charset);
    }

    public org.apache.commons.mail.Email addCc(final java.lang.String email, final java.lang.String name, final java.lang.String charset) throws org.apache.commons.mail.EmailException {
        this.ccList.add(createInternetAddress(email, name, charset));
        return this;
    }

    public org.apache.commons.mail.Email setCc(final java.util.Collection<javax.mail.internet.InternetAddress> aCollection) throws org.apache.commons.mail.EmailException {
        if ((aCollection == null) || (aCollection.isEmpty())) {
            return null;
        } 
        this.ccList = new java.util.ArrayList<javax.mail.internet.InternetAddress>(aCollection);
        return this;
    }

    public org.apache.commons.mail.Email addBcc(final java.lang.String email) throws org.apache.commons.mail.EmailException {
        return addBcc(email, null);
    }

    public org.apache.commons.mail.Email addBcc(final java.lang.String... emails) throws org.apache.commons.mail.EmailException {
        if ((emails == null) || ((emails.length) == 0)) {
            return null;
        } 
        for (final java.lang.String email : emails) {
            addBcc(email, null);
        }
        return this;
    }

    public org.apache.commons.mail.Email addBcc(final java.lang.String email, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        return addBcc(email, name, this.charset);
    }

    public org.apache.commons.mail.Email addBcc(final java.lang.String email, final java.lang.String name, final java.lang.String charset) throws org.apache.commons.mail.EmailException {
        this.bccList.add(createInternetAddress(email, name, charset));
        return this;
    }

    public org.apache.commons.mail.Email setBcc(final java.util.Collection<javax.mail.internet.InternetAddress> aCollection) throws org.apache.commons.mail.EmailException {
        if ((aCollection == null) || (aCollection.isEmpty())) {
            return null;
        } 
        this.bccList = new java.util.ArrayList<javax.mail.internet.InternetAddress>(aCollection);
        return this;
    }

    public org.apache.commons.mail.Email addReplyTo(final java.lang.String email) throws org.apache.commons.mail.EmailException {
        return addReplyTo(email, null);
    }

    public org.apache.commons.mail.Email addReplyTo(final java.lang.String email, final java.lang.String name) throws org.apache.commons.mail.EmailException {
        return addReplyTo(email, name, this.charset);
    }

    public org.apache.commons.mail.Email addReplyTo(final java.lang.String email, final java.lang.String name, final java.lang.String charset) throws org.apache.commons.mail.EmailException {
        this.replyList.add(createInternetAddress(email, name, charset));
        return this;
    }

    public org.apache.commons.mail.Email setReplyTo(final java.util.Collection<javax.mail.internet.InternetAddress> aCollection) throws org.apache.commons.mail.EmailException {
        if ((aCollection == null) || (aCollection.isEmpty())) {
            return null;
        } 
        this.replyList = new java.util.ArrayList<javax.mail.internet.InternetAddress>(aCollection);
        return this;
    }

    public void setHeaders(final java.util.Map<java.lang.String, java.lang.String> map) {
        this.headers.clear();
        for (final java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            addHeader(entry.getKey(), entry.getValue());
        }
    }

    public void addHeader(final java.lang.String name, final java.lang.String value) {
        if (org.apache.commons.mail.EmailUtils.isEmpty(name)) {
            return;
        } 
        if (org.apache.commons.mail.EmailUtils.isEmpty(value)) {
            return;
        } 
        this.headers.put(name, value);
    }

    public org.apache.commons.mail.Email setSubject(final java.lang.String aSubject) {
        this.subject = aSubject;
        return this;
    }

    public java.lang.String getBounceAddress() {
        return this.bounceAddress;
    }

    public org.apache.commons.mail.Email setBounceAddress(final java.lang.String email) {
        checkSessionAlreadyInitialized();
        this.bounceAddress = email;
        return this;
    }

    public abstract org.apache.commons.mail.Email setMsg(java.lang.String msg) throws org.apache.commons.mail.EmailException;

    public void buildMimeMessage() throws org.apache.commons.mail.EmailException {
        if ((this.message) != null) {
            return;
        } 
        try {
            this.message = createMimeMessage(getMailSession());
            if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.subject)) {
                if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.charset)) {
                    this.message.setSubject(this.subject, this.charset);
                } else {
                    this.message.setSubject(this.subject);
                }
            } 
            updateContentType(this.contentType);
            if ((this.content) != null) {
                if ((org.apache.commons.mail.EmailConstants.TEXT_PLAIN.equalsIgnoreCase(this.contentType)) && ((this.content) instanceof java.lang.String)) {
                    this.message.setText(this.content.toString(), this.charset);
                } else {
                    this.message.setContent(this.content, this.contentType);
                }
            } else if ((this.emailBody) != null) {
                if ((this.contentType) == null) {
                    this.message.setContent(this.emailBody);
                } else {
                    this.message.setContent(this.emailBody, this.contentType);
                }
            } else {
                this.message.setText("");
            }
            if ((this.fromAddress) != null) {
                this.message.setFrom(this.fromAddress);
            } else {
                if (((session.getProperty(org.apache.commons.mail.EmailConstants.MAIL_SMTP_FROM)) == null) && ((session.getProperty(org.apache.commons.mail.EmailConstants.MAIL_FROM)) == null)) {
                    throw new org.apache.commons.mail.EmailException("From address required");
                } 
            }
            if ((((this.toList.size()) + (this.ccList.size())) + (this.bccList.size())) == 0) {
                throw new org.apache.commons.mail.EmailException("At least one receiver address required");
            } 
            if ((this.toList.size()) > 0) {
                this.message.setRecipients(javax.mail.Message.RecipientType.TO, toInternetAddressArray(this.toList));
            } 
            if ((this.ccList.size()) > 0) {
                this.message.setRecipients(javax.mail.Message.RecipientType.CC, toInternetAddressArray(this.ccList));
            } 
            if ((this.bccList.size()) > 0) {
                this.message.setRecipients(javax.mail.Message.RecipientType.BCC, toInternetAddressArray(this.bccList));
            } 
            if ((this.replyList.size()) > 0) {
                this.message.setReplyTo(toInternetAddressArray(this.replyList));
            } 
            if ((this.headers.size()) > 0) {
                for (final java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.headers.entrySet()) {
                    final java.lang.String foldedValue = createFoldedHeaderValue(entry.getKey(), entry.getValue());
                    this.message.addHeader(entry.getKey(), foldedValue);
                }
            } 
            if ((this.message.getSentDate()) == null) {
                this.message.setSentDate(getSentDate());
            } 
            if (this.popBeforeSmtp) {
                final javax.mail.Store store = session.getStore("pop3");
                store.connect(this.popHost, this.popUsername, this.popPassword);
            } 
        } catch (final javax.mail.MessagingException me) {
            return;
        }
    }

    public java.lang.String sendMimeMessage() throws org.apache.commons.mail.EmailException {
        org.apache.commons.mail.EmailUtils.notNull(this.message, "MimeMessage has not been created yet");
        try {
            javax.mail.Transport.send(this.message);
            return this.message.getMessageID();
        } catch (final java.lang.Throwable t) {
            final java.lang.String msg = (("Sending the email to the following server failed : " + (getHostName())) + ":") + (getSmtpPort());
            return new java.lang.String();
        }
    }

    public javax.mail.internet.MimeMessage getMimeMessage() {
        return this.message;
    }

    public java.lang.String send() throws org.apache.commons.mail.EmailException {
        buildMimeMessage();
        return sendMimeMessage();
    }

    public void setSentDate(final java.util.Date date) {
        if (date != null) {
            this.sentDate = new java.util.Date(date.getTime());
        } 
    }

    public java.util.Date getSentDate() {
        if ((this.sentDate) == null) {
            return new java.util.Date();
        } 
        return new java.util.Date(this.sentDate.getTime());
    }

    public java.lang.String getSubject() {
        return this.subject;
    }

    public javax.mail.internet.InternetAddress getFromAddress() {
        return this.fromAddress;
    }

    public java.lang.String getHostName() {
        if ((this.session) != null) {
            return this.session.getProperty(MAIL_HOST);
        } else if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.hostName)) {
            return this.hostName;
        } 
        return null;
    }

    public java.lang.String getSmtpPort() {
        if ((this.session) != null) {
            return this.session.getProperty(MAIL_PORT);
        } else if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.smtpPort)) {
            return this.smtpPort;
        } 
        return null;
    }

    public boolean isStartTLSRequired() {
        return this.startTlsRequired;
    }

    public boolean isStartTLSEnabled() {
        return (this.startTlsEnabled) || (tls);
    }

    @java.lang.Deprecated
    public boolean isTLS() {
        return isStartTLSEnabled();
    }

    protected javax.mail.internet.InternetAddress[] toInternetAddressArray(final java.util.List<javax.mail.internet.InternetAddress> list) {
        return list.toArray(new javax.mail.internet.InternetAddress[list.size()]);
    }

    public void setPopBeforeSmtp(final boolean newPopBeforeSmtp, final java.lang.String newPopHost, final java.lang.String newPopUsername, final java.lang.String newPopPassword) {
        this.popBeforeSmtp = newPopBeforeSmtp;
        this.popHost = newPopHost;
        this.popUsername = newPopUsername;
        this.popPassword = newPopPassword;
    }

    @java.lang.Deprecated
    public boolean isSSL() {
        return isSSLOnConnect();
    }

    public boolean isSSLOnConnect() {
        return (sslOnConnect) || (ssl);
    }

    @java.lang.Deprecated
    public void setSSL(final boolean ssl) {
        setSSLOnConnect(ssl);
    }

    public org.apache.commons.mail.Email setSSLOnConnect(final boolean ssl) {
        checkSessionAlreadyInitialized();
        this.sslOnConnect = ssl;
        this.ssl = ssl;
        return this;
    }

    public boolean isSSLCheckServerIdentity() {
        return sslCheckServerIdentity;
    }

    public org.apache.commons.mail.Email setSSLCheckServerIdentity(final boolean sslCheckServerIdentity) {
        checkSessionAlreadyInitialized();
        this.sslCheckServerIdentity = sslCheckServerIdentity;
        return this;
    }

    public java.lang.String getSslSmtpPort() {
        if ((this.session) != null) {
            return this.session.getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT);
        } else if (org.apache.commons.mail.EmailUtils.isNotEmpty(this.sslSmtpPort)) {
            return this.sslSmtpPort;
        } 
        return null;
    }

    public void setSslSmtpPort(final java.lang.String sslSmtpPort) {
        checkSessionAlreadyInitialized();
        this.sslSmtpPort = sslSmtpPort;
    }

    public boolean isSendPartial() {
        return sendPartial;
    }

    public org.apache.commons.mail.Email setSendPartial(final boolean sendPartial) {
        checkSessionAlreadyInitialized();
        this.sendPartial = sendPartial;
        return this;
    }

    public java.util.List<javax.mail.internet.InternetAddress> getToAddresses() {
        return this.toList;
    }

    public java.util.List<javax.mail.internet.InternetAddress> getCcAddresses() {
        return this.ccList;
    }

    public java.util.List<javax.mail.internet.InternetAddress> getBccAddresses() {
        return this.bccList;
    }

    public java.util.List<javax.mail.internet.InternetAddress> getReplyToAddresses() {
        return this.replyList;
    }

    public int getSocketConnectionTimeout() {
        return this.socketConnectionTimeout;
    }

    public void setSocketConnectionTimeout(final int socketConnectionTimeout) {
        checkSessionAlreadyInitialized();
        this.socketConnectionTimeout = socketConnectionTimeout;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(final int socketTimeout) {
        checkSessionAlreadyInitialized();
        this.socketTimeout = socketTimeout;
    }

    protected javax.mail.internet.MimeMessage createMimeMessage(final javax.mail.Session aSession) {
        return new javax.mail.internet.MimeMessage(aSession);
    }

    private java.lang.String createFoldedHeaderValue(final java.lang.String name, final java.lang.Object value) {
        java.lang.String result;
        if (org.apache.commons.mail.EmailUtils.isEmpty(name)) {
            return new java.lang.String();
        } 
        if ((value == null) || (org.apache.commons.mail.EmailUtils.isEmpty(value.toString()))) {
            return new java.lang.String();
        } 
        try {
            result = javax.mail.internet.MimeUtility.fold(((name.length()) + 2), javax.mail.internet.MimeUtility.encodeText(value.toString(), this.charset, null));
        } catch (final java.io.UnsupportedEncodingException e) {
            result = value.toString();
        }
        return result;
    }

    private javax.mail.internet.InternetAddress createInternetAddress(final java.lang.String email, final java.lang.String name, final java.lang.String charsetName) throws org.apache.commons.mail.EmailException {
        javax.mail.internet.InternetAddress address = null;
        try {
            address = new javax.mail.internet.InternetAddress(email);
            if (org.apache.commons.mail.EmailUtils.isNotEmpty(name)) {
                if (org.apache.commons.mail.EmailUtils.isEmpty(charsetName)) {
                    address.setPersonal(name);
                } else {
                    final java.nio.charset.Charset set = java.nio.charset.Charset.forName(charsetName);
                    address.setPersonal(name, set.name());
                }
            } 
            address.validate();
        } catch (final javax.mail.internet.AddressException e) {
            return new javax.mail.internet.InternetAddress();
        } catch (final java.io.UnsupportedEncodingException e) {
            return new javax.mail.internet.InternetAddress();
        }
        return address;
    }

    private void checkSessionAlreadyInitialized() {
        if ((this.session) != null) {
            return;
        } 
    }
}

