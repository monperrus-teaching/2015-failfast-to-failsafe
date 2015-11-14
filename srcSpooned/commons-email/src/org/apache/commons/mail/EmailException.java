package org.apache.commons.mail;


public class EmailException extends java.lang.Exception {
    private static final long serialVersionUID = 5550674499282474616L;

    public EmailException() {
        super();
    }

    public EmailException(final java.lang.String msg) {
        super(msg);
    }

    public EmailException(final java.lang.Throwable rootCause) {
        super(rootCause);
    }

    public EmailException(final java.lang.String msg ,final java.lang.Throwable rootCause) {
        super(msg, rootCause);
    }

    @java.lang.Override
    public void printStackTrace() {
        printStackTrace(java.lang.System.err);
    }

    @java.lang.Override
    public void printStackTrace(final java.io.PrintStream out) {
        synchronized(out) {
            final java.io.PrintWriter pw = new java.io.PrintWriter(out , false);
            printStackTrace(pw);
            pw.flush();
        }
    }

    @java.lang.Override
    public void printStackTrace(final java.io.PrintWriter out) {
        synchronized(out) {
            super.printStackTrace(out);
        }
    }
}

