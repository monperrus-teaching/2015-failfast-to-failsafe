package org.apache.commons.mail;


public class SimpleEmail extends org.apache.commons.mail.Email {
    @java.lang.Override
    public org.apache.commons.mail.Email setMsg(final java.lang.String msg) throws org.apache.commons.mail.EmailException {
        if (org.apache.commons.mail.EmailUtils.isEmpty(msg)) {
            throw new org.apache.commons.mail.EmailException("Invalid message supplied");
        } 
        setContent(msg, org.apache.commons.mail.EmailConstants.TEXT_PLAIN);
        return this;
    }
}

