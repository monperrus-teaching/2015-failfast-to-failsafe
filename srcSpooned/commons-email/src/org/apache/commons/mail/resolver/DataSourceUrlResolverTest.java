package org.apache.commons.mail.resolver;


public class DataSourceUrlResolverTest extends org.apache.commons.mail.resolver.AbstractDataSourceResolverTest {
    @org.junit.Test
    public void testResolvingFilesLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceUrlResolver(new java.io.File("./src/test/resources").toURI().toURL() , true);
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("./images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertNull(dataSourceResolver.resolve("./images/does-not-exist.gif"));
        org.junit.Assert.assertNull(dataSourceResolver.resolve("/images/asf_logo_wide.gif"));
    }

    @org.junit.Test
    public void testResolvingHttpLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceUrlResolver(new java.net.URL("http://www.apache.org") , true);
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("http://www.apache.org/images/feather-small.gif")).length) > 1));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("images/feather-small.gif")).length) > 1));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("./images/feather-small.gif")).length) > 1));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("/images/feather-small.gif")).length) > 1));
        org.junit.Assert.assertNull(toByteArray(dataSourceResolver.resolve("/images/does-not-exist.gif")));
    }

    @org.junit.Test
    public void testResolvingHttpNonLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceUrlResolver(new java.net.URL("http://www.apache.org") , false);
        org.junit.Assert.assertNotNull(dataSourceResolver.resolve("images/asf_logo_wide.gif"));
        dataSourceResolver.resolve("images/does-not-exist.gif");
    }
}

