package org.apache.commons.mail.resolver;


public abstract class AbstractDataSourceResolverTest {
    protected final int IMG_SIZE = 5866;

    protected byte[] toByteArray(final javax.activation.DataSource dataSource) throws java.io.IOException {
        if (dataSource != null) {
            java.io.InputStream is = dataSource.getInputStream();
            try {
                return org.apache.commons.io.IOUtils.toByteArray(is);
            } finally {
                is.close();
            }
        } 
        return null;
    }
}

