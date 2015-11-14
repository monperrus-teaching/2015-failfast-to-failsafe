package org.apache.commons.mail.mocks;


public class MockHtmlEmailConcrete extends org.apache.commons.mail.HtmlEmail {
    public java.lang.String getMsg() {
        try {
            return getPrimaryBodyPart().getContent().toString();
        } catch (final java.io.IOException ioE) {
            return null;
        } catch (final javax.mail.MessagingException msgE) {
            return null;
        }
    }

    public java.lang.String getTextMsg() {
        return this.text;
    }

    public java.lang.String getHtmlMsg() {
        return this.html;
    }

    @java.lang.SuppressWarnings(value = "rawtypes")
    public java.util.Map getInlineEmbeds() {
        return inlineEmbeds;
    }

    @java.lang.Override
    public javax.mail.internet.InternetAddress getFromAddress() {
        return this.fromAddress;
    }
}

