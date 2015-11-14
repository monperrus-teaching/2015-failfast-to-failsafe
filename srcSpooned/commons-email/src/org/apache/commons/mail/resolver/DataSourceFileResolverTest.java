package org.apache.commons.mail.resolver;


public class DataSourceFileResolverTest extends org.apache.commons.mail.resolver.AbstractDataSourceResolverTest {
    @org.junit.Test
    public void testResolvingFileLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceFileResolver(new java.io.File("./src/test/resources") , true);
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("./images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("../resources/images/asf_logo_wide.gif")).length) == (IMG_SIZE)));
        org.junit.Assert.assertNull(toByteArray(dataSourceResolver.resolve("/images/does-not-exist.gif")));
        org.junit.Assert.assertNull(dataSourceResolver.resolve("./images/does-not-exist.gif"));
    }

    @org.junit.Test
    public void testResolvingFileNonLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceFileResolver(new java.io.File(".") , false);
        org.junit.Assert.assertNotNull(dataSourceResolver.resolve("./src/test/resources/images/asf_logo_wide.gif"));
        dataSourceResolver.resolve("asf_logo_wide.gif");
    }
}

