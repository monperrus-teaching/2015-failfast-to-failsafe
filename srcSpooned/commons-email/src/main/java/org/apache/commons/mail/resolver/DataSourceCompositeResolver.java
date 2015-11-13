package org.apache.commons.mail.resolver;


public class DataSourceCompositeResolver extends org.apache.commons.mail.resolver.DataSourceBaseResolver {
    private final org.apache.commons.mail.DataSourceResolver[] dataSourceResolvers;

    public DataSourceCompositeResolver(final org.apache.commons.mail.DataSourceResolver[] dataSourceResolvers) {
        this.dataSourceResolvers = new org.apache.commons.mail.DataSourceResolver[dataSourceResolvers.length];
        java.lang.System.arraycopy(dataSourceResolvers, 0, this.dataSourceResolvers, 0, dataSourceResolvers.length);
    }

    public DataSourceCompositeResolver(final org.apache.commons.mail.DataSourceResolver[] dataSourceResolvers ,final boolean isLenient) {
        super(isLenient);
        this.dataSourceResolvers = new org.apache.commons.mail.DataSourceResolver[dataSourceResolvers.length];
        java.lang.System.arraycopy(dataSourceResolvers, 0, this.dataSourceResolvers, 0, dataSourceResolvers.length);
    }

    public org.apache.commons.mail.DataSourceResolver[] getDataSourceResolvers() {
        final org.apache.commons.mail.DataSourceResolver[] resolvers = new org.apache.commons.mail.DataSourceResolver[dataSourceResolvers.length];
        java.lang.System.arraycopy(dataSourceResolvers, 0, resolvers, 0, dataSourceResolvers.length);
        return resolvers;
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation) throws java.io.IOException {
        final javax.activation.DataSource result = resolve(resourceLocation, true);
        if ((isLenient()) || (result != null)) {
            return result;
        } 
        return null;
    }

    public javax.activation.DataSource resolve(final java.lang.String resourceLocation, final boolean isLenient) throws java.io.IOException {
        for (int i = 0 ; i < (getDataSourceResolvers().length) ; i++) {
            final org.apache.commons.mail.DataSourceResolver dataSourceResolver = getDataSourceResolvers()[i];
            final javax.activation.DataSource dataSource = dataSourceResolver.resolve(resourceLocation, isLenient);
            if (dataSource != null) {
                return dataSource;
            } 
        }
        if (isLenient) {
            return null;
        } 
        return null;
    }
}

