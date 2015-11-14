package org.apache.commons.mail;


public class EmailAttachment {
    public static final java.lang.String ATTACHMENT = javax.mail.Part.ATTACHMENT;

    public static final java.lang.String INLINE = javax.mail.Part.INLINE;

    private java.lang.String name = "";

    private java.lang.String description = "";

    private java.lang.String path = "";

    private java.net.URL url;

    private java.lang.String disposition = ATTACHMENT;

    public java.lang.String getDescription() {
        return description;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.lang.String getPath() {
        return path;
    }

    public java.net.URL getURL() {
        return url;
    }

    public java.lang.String getDisposition() {
        return disposition;
    }

    public void setDescription(final java.lang.String desc) {
        this.description = desc;
    }

    public void setName(final java.lang.String aName) {
        this.name = aName;
    }

    public void setPath(final java.lang.String aPath) {
        this.path = aPath;
    }

    public void setURL(final java.net.URL aUrl) {
        this.url = aUrl;
    }

    public void setDisposition(final java.lang.String aDisposition) {
        this.disposition = aDisposition;
    }
}

