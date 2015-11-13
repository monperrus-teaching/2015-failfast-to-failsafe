package org.apache.commons.mail.resolver;


public class DataSourceClassPathResolver extends org.apache.commons.mail.resolver.DataSourceBaseResolver {
    private final java.lang.String classPathBase;

    public DataSourceClassPathResolver() {
        this.classPathBase = "/";
    }

    public DataSourceClassPathResolver(final java.lang.String classPathBase) {
        this.classPathBase = classPathBase.endsWith("/") ? classPathBase : classPathBase + "/";
    }

    public DataSourceClassPathResolver(final java.lang.String classPathBase ,final boolean lenient) {
        super(lenient);
        this.classPathBase = classPathBase.endsWith("/") ? classPathBase : classPathBase + "/";
    }

    public java.lang.String getClassPathBase() {
        return classPathBase;
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation) throws java.io.IOException {
        return resolve(resourceLocation, isLenient());
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation, final boolean isLenient) throws java.io.IOException {
        javax.activation.DataSource result = null;
        try {
            if ((!(isCid(resourceLocation))) && (!(isHttpUrl(resourceLocation)))) {
                final java.lang.String mimeType = javax.activation.FileTypeMap.getDefaultFileTypeMap().getContentType(resourceLocation);
                final java.lang.String resourceName = getResourceName(resourceLocation);
                final java.io.InputStream is = org.apache.commons.mail.resolver.DataSourceClassPathResolver.class.getResourceAsStream(resourceName);
                if (is != null) {
                    final javax.mail.util.ByteArrayDataSource ds = new javax.mail.util.ByteArrayDataSource(is , mimeType);
                    ds.setName(org.apache.commons.mail.resolver.DataSourceClassPathResolver.class.getResource(resourceName).toString());
                    result = ds;
                } else {
                    if (isLenient) {
                        return null;
                    } 
                    throw new java.io.IOException(("The following class path resource was not found : " + resourceLocation));
                }
            } 
            return result;
        } catch (final java.io.IOException e) {
            if (isLenient) {
                return null;
            } 
            return null;
        }
    }

    private java.lang.String getResourceName(final java.lang.String resourceLocation) {
        return ((getClassPathBase()) + resourceLocation).replaceAll("//", "/");
    }
}

