package org.apache.commons.mail;


public class ImageHtmlEmail extends org.apache.commons.mail.HtmlEmail {
    public static final java.lang.String REGEX_IMG_SRC = "(<[Ii][Mm][Gg]\\s*[^>]*?\\s+[Ss][Rr][Cc]\\s*=\\s*[\"\'])([^\"\']+?)([\"\'])";

    public static final java.lang.String REGEX_SCRIPT_SRC = "(<[Ss][Cc][Rr][Ii][Pp][Tt]\\s*.*?\\s+[Ss][Rr][Cc]\\s*=\\s*[\"\'])([^\"\']+?)([\"\'])";

    private static final java.util.regex.Pattern IMG_PATTERN = java.util.regex.Pattern.compile(REGEX_IMG_SRC);

    private static final java.util.regex.Pattern SCRIPT_PATTERN = java.util.regex.Pattern.compile(REGEX_SCRIPT_SRC);

    private org.apache.commons.mail.DataSourceResolver dataSourceResolver;

    public org.apache.commons.mail.DataSourceResolver getDataSourceResolver() {
        return dataSourceResolver;
    }

    public void setDataSourceResolver(final org.apache.commons.mail.DataSourceResolver dataSourceResolver) {
        this.dataSourceResolver = dataSourceResolver;
    }

    @java.lang.Override
    public void buildMimeMessage() throws org.apache.commons.mail.EmailException {
        try {
            java.lang.String temp = replacePattern(super.html, IMG_PATTERN);
            temp = replacePattern(temp, SCRIPT_PATTERN);
            setHtmlMsg(temp);
            super.buildMimeMessage();
        } catch (final java.io.IOException e) {
            throw new org.apache.commons.mail.EmailException("Building the MimeMessage failed" , e);
        }
    }

    private java.lang.String replacePattern(final java.lang.String htmlMessage, final java.util.regex.Pattern pattern) throws java.io.IOException, org.apache.commons.mail.EmailException {
        javax.activation.DataSource dataSource;
        final java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        final java.util.Map<java.lang.String, java.lang.String> cidCache = new java.util.HashMap<java.lang.String, java.lang.String>();
        final java.util.Map<java.lang.String, javax.activation.DataSource> dataSourceCache = new java.util.HashMap<java.lang.String, javax.activation.DataSource>();
        final java.util.regex.Matcher matcher = pattern.matcher(htmlMessage);
        while (matcher.find()) {
            final java.lang.String resourceLocation = matcher.group(2);
            if ((dataSourceCache.get(resourceLocation)) == null) {
                dataSource = getDataSourceResolver().resolve(resourceLocation);
                if (dataSource != null) {
                    dataSourceCache.put(resourceLocation, dataSource);
                } 
            } else {
                dataSource = dataSourceCache.get(resourceLocation);
            }
            if (dataSource != null) {
                java.lang.String name = dataSource.getName();
                if (org.apache.commons.mail.EmailUtils.isEmpty(name)) {
                    name = resourceLocation;
                } 
                java.lang.String cid = cidCache.get(name);
                if (cid == null) {
                    cid = embed(dataSource, name);
                    cidCache.put(name, cid);
                } 
                matcher.appendReplacement(stringBuffer, java.util.regex.Matcher.quoteReplacement(((((matcher.group(1)) + "cid:") + cid) + (matcher.group(3)))));
            } 
        }
        matcher.appendTail(stringBuffer);
        cidCache.clear();
        dataSourceCache.clear();
        return stringBuffer.toString();
    }
}

