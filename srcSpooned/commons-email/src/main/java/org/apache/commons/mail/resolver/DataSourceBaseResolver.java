package org.apache.commons.mail.resolver;


public abstract class DataSourceBaseResolver implements org.apache.commons.mail.DataSourceResolver {
    private final boolean lenient;

    public DataSourceBaseResolver() {
        this.lenient = false;
    }

    public DataSourceBaseResolver(final boolean lenient) {
        this.lenient = lenient;
    }

    public boolean isLenient() {
        return lenient;
    }

    protected boolean isCid(final java.lang.String resourceLocation) {
        return resourceLocation.startsWith("cid:");
    }

    protected boolean isFileUrl(final java.lang.String urlString) {
        return urlString.startsWith("file:/");
    }

    protected boolean isHttpUrl(final java.lang.String urlString) {
        return (urlString.startsWith("http://")) || (urlString.startsWith("https://"));
    }
}

