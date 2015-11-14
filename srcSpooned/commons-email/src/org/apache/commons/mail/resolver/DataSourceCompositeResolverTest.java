package org.apache.commons.mail.resolver;


public class DataSourceCompositeResolverTest extends org.apache.commons.mail.resolver.AbstractDataSourceResolverTest {
    private org.apache.commons.mail.DataSourceResolver[] dataSourceResolvers;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        final org.apache.commons.mail.resolver.DataSourceUrlResolver urlResolver = new org.apache.commons.mail.resolver.DataSourceUrlResolver(new java.net.URL("http://www.apache.org") , false);
        final org.apache.commons.mail.resolver.DataSourceClassPathResolver classPathResolver = new org.apache.commons.mail.resolver.DataSourceClassPathResolver("/images" , false);
        dataSourceResolvers = new org.apache.commons.mail.DataSourceResolver[]{ urlResolver , classPathResolver };
    }

    @org.junit.Test
    public void testResolvingFilesLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceCompositeResolver(dataSourceResolvers , true);
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("/images/feather-small.gif")).length) > 0));
        org.junit.Assert.assertTrue(((toByteArray(dataSourceResolver.resolve("/contentTypeTest.gif")).length) > 0));
    }

    @org.junit.Test
    public void testResolvingFilesNonLenient() throws java.lang.Exception {
        final org.apache.commons.mail.DataSourceResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceCompositeResolver(dataSourceResolvers , false);
        dataSourceResolver.resolve("./image/does-not-exist.gif");
    }

    @org.junit.Test
    public void testExternalModification() throws java.lang.Exception {
        final org.apache.commons.mail.resolver.DataSourceCompositeResolver dataSourceResolver = new org.apache.commons.mail.resolver.DataSourceCompositeResolver(dataSourceResolvers , true);
        final org.apache.commons.mail.DataSourceResolver[] arr = dataSourceResolver.getDataSourceResolvers();
        arr[0] = null;
        final org.apache.commons.mail.DataSourceResolver[] arr2 = dataSourceResolver.getDataSourceResolvers();
        org.junit.Assert.assertNotNull(arr2[0]);
    }
}

