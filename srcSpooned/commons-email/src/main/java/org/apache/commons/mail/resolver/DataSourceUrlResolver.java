package org.apache.commons.mail.resolver;


public class DataSourceUrlResolver extends org.apache.commons.mail.resolver.DataSourceBaseResolver {
    private final java.net.URL baseUrl;

    public DataSourceUrlResolver(final java.net.URL baseUrl) {
        super();
        this.baseUrl = baseUrl;
    }

    public DataSourceUrlResolver(final java.net.URL baseUrl ,final boolean lenient) {
        super(lenient);
        this.baseUrl = baseUrl;
    }

    public java.net.URL getBaseUrl() {
        return baseUrl;
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation) throws java.io.IOException {
        return resolve(resourceLocation, isLenient());
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation, final boolean isLenient) throws java.io.IOException {
        javax.activation.DataSource result = null;
        try {
            if (!(isCid(resourceLocation))) {
                final java.net.URL url = createUrl(resourceLocation);
                result = new javax.activation.URLDataSource(url);
                result.getInputStream();
            } 
            return result;
        } catch (final java.io.IOException e) {
            if (isLenient) {
                return null;
            } 
            return null;
        }
    }

    protected java.net.URL createUrl(final java.lang.String resourceLocation) throws java.net.MalformedURLException {
        if ((baseUrl) == null) {
            return new java.net.URL(resourceLocation);
        } 
        if ((resourceLocation == null) || ((resourceLocation.length()) == 0)) {
            return new java.net.URL(new java.lang.String());
        } 
        if ((isFileUrl(resourceLocation)) || (isHttpUrl(resourceLocation))) {
            return new java.net.URL(resourceLocation);
        } 
        return new java.net.URL(getBaseUrl() , resourceLocation.replaceAll("&amp;", "&"));
    }
}

