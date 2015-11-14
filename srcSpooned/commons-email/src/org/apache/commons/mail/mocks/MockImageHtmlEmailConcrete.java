package org.apache.commons.mail.mocks;


public class MockImageHtmlEmailConcrete extends org.apache.commons.mail.ImageHtmlEmail {
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
}

