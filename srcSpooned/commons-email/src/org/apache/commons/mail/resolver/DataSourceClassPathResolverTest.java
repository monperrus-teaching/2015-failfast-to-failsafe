package org.apache.commons.mail.resolver;


public class DataSourceClassPathResolverTest extends org.apache.commons.mail.resolver.AbstractDataSourceResolverTest {
    @org.junit.Test
    public void testResolvingClassPathLenient() throws java.lang.Exception {
        org.apache.commons.mail.DataSourceResolver dataSourceResolver;
        dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/" , true);
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("./images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("/images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertNull(dataSourceResolver.resolve("/asf_logo_wide.gif"));
        dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/images" , true);
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("./asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertNull(dataSourceResolver.resolve("./images/asf_logo_wide.gif"));
    }

    @org.junit.Test
    public void testResolvingClassPathNonLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/" , false);
        org.junit.Assert.assertNotNull(dataSourceResolver.resolve("images/asf_logo_wide.gif"));
        dataSourceResolver.resolve("asf_logo_wide.gif");
    }
}

