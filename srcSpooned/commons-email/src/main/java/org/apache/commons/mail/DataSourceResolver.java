package org.apache.commons.mail;


public interface DataSourceResolver {
    javax.activation.DataSource resolve(final java.lang.String resourceLocation) throws java.io.IOException;

    javax.activation.DataSource resolve(final java.lang.String resourceLocation, final boolean isLenient) throws java.io.IOException;
}

