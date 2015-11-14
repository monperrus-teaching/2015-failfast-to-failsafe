package org.apache.commons.mail.settings;


public final class EmailConfiguration {
    public static final boolean MAIL_FORCE_SEND = false;

    public static final boolean MAIL_DEBUG = false;

    public static final java.lang.String MAIL_CHARSET = org.apache.commons.mail.EmailConstants.UTF_8;

    public static final java.lang.String MAIL_SERVER = "localhost";

    public static final int MAIL_SERVER_PORT = 25;

    public static final java.lang.String TEST_FROM = "test_from@apache.org";

    public static final java.lang.String TEST_TO = "test_to@apache.org";

    public static final java.lang.String TEST_USER = "user";

    public static final java.lang.String TEST_PASSWD = "password";

    public static final boolean MAIL_USE_SSL = false;

    public static final boolean MAIL_SSL_CHECKSERVERIDENTITY = false;

    public static final boolean MAIL_USE_STARTTLS = true;

    public static final boolean MAIL_STARTTLS_REQUIRED = true;

    public static final java.lang.String TEST_URL = org.apache.commons.mail.settings.EmailConfiguration.class.getResource("/images/asf_logo_wide.gif").toExternalForm();

    public static final int TIME_OUT = 500;
}

