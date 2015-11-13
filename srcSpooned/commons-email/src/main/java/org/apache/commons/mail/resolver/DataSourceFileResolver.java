package org.apache.commons.mail.resolver;


public class DataSourceFileResolver extends org.apache.commons.mail.resolver.DataSourceBaseResolver {
    private final java.io.File baseDir;

    public DataSourceFileResolver() {
        baseDir = new java.io.File(".");
    }

    public DataSourceFileResolver(final java.io.File baseDir) {
        this.baseDir = baseDir;
    }

    public DataSourceFileResolver(final java.io.File baseDir ,final boolean lenient) {
        super(lenient);
        this.baseDir = baseDir;
    }

    public java.io.File getBaseDir() {
        return baseDir;
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation) throws java.io.IOException {
        return resolve(resourceLocation, isLenient());
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation, final boolean isLenient) throws java.io.IOException {
        java.io.File file;
        javax.activation.DataSource result = null;
        if (!(isCid(resourceLocation))) {
            file = new java.io.File(resourceLocation);
            if (!(file.isAbsolute())) {
                file = (getBaseDir()) != null ? new java.io.File(getBaseDir() , resourceLocation) : new java.io.File(resourceLocation);
            } 
            if (file.exists()) {
                result = new javax.activation.FileDataSource(file);
            } else {
                if (!isLenient) {
                    return null;
                } 
            }
        } 
        return result;
    }
}

