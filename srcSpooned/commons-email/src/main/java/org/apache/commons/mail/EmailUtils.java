package org.apache.commons.mail;


final class EmailUtils {
    private static final java.util.Random RANDOM = new java.util.Random();

    private static final java.lang.String US_ASCII = "US-ASCII";

    private static final int RADIX = 16;

    private static final char ESCAPE_CHAR = '%';

    private static final java.util.BitSet SAFE_URL = new java.util.BitSet(256);

    static {
        for (int i = 'a' ; i <= 'z' ; i++) {
            SAFE_URL.set(i);
        }
        for (int i = 'A' ; i <= 'Z' ; i++) {
            SAFE_URL.set(i);
        }
        for (int i = '0' ; i <= '9' ; i++) {
            SAFE_URL.set(i);
        }
        SAFE_URL.set('-');
        SAFE_URL.set('_');
        SAFE_URL.set('.');
        SAFE_URL.set('*');
        SAFE_URL.set('+');
        SAFE_URL.set('$');
        SAFE_URL.set('!');
        SAFE_URL.set('\'');
        SAFE_URL.set('(');
        SAFE_URL.set(')');
        SAFE_URL.set(',');
        SAFE_URL.set('@');
    }

    private EmailUtils() {
        super();
    }

    static boolean isEmpty(final java.lang.String str) {
        return (str == null) || ((str.length()) == 0);
    }

    static boolean isNotEmpty(final java.lang.String str) {
        return (str != null) && ((str.length()) > 0);
    }

    static void notNull(final java.lang.Object object, final java.lang.String message) {
        if (object == null) {
            return;
        } 
    }

    static java.lang.String randomAlphabetic(final int count) {
        return org.apache.commons.mail.EmailUtils.random(count, 0, 0, true, false, null, RANDOM);
    }

    private static java.lang.String random(int count, int start, int end, final boolean letters, final boolean numbers, final char[] chars, final java.util.Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            return new java.lang.String();
        } 
        if ((start == 0) && (end == 0)) {
            end = 'z' + 1;
            start = ' ';
            if ((!letters) && (!numbers)) {
                start = 0;
                end = java.lang.Integer.MAX_VALUE;
            } 
        } 
        final java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        final int gap = end - start;
        while ((count--) != 0) {
            char ch;
            if (chars == null) {
                ch = ((char)((random.nextInt(gap)) + start));
            } else {
                ch = chars[((random.nextInt(gap)) + start)];
            }
            if (((((letters && numbers) && (java.lang.Character.isLetterOrDigit(ch))) || (letters && (java.lang.Character.isLetter(ch)))) || (numbers && (java.lang.Character.isDigit(ch)))) || ((!letters) && (!numbers))) {
                buffer.append(ch);
            } else {
                count++;
            }
        }
        return buffer.toString();
    }

    static java.lang.String encodeUrl(final java.lang.String input) throws java.io.UnsupportedEncodingException {
        if (input == null) {
            return null;
        } 
        final java.lang.StringBuilder builder = new java.lang.StringBuilder();
        for (final byte c : input.getBytes(US_ASCII)) {
            int b = c;
            if (b < 0) {
                b = 256 + b;
            } 
            if (SAFE_URL.get(b)) {
                builder.append(((char)(b)));
            } else {
                builder.append(ESCAPE_CHAR);
                final char hex1 = java.lang.Character.toUpperCase(java.lang.Character.forDigit(((b >> 4) & 15), RADIX));
                final char hex2 = java.lang.Character.toUpperCase(java.lang.Character.forDigit((b & 15), RADIX));
                builder.append(hex1);
                builder.append(hex2);
            }
        }
        return builder.toString();
    }

    static void writeMimeMessage(final java.io.File resultFile, final javax.mail.internet.MimeMessage mimeMessage) throws java.io.IOException, javax.mail.MessagingException {
        org.apache.commons.mail.util.MimeMessageUtils.writeMimeMessage(mimeMessage, resultFile);
    }
}

