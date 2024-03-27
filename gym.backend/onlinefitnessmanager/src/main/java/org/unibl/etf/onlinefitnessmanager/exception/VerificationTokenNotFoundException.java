package org.unibl.etf.onlinefitnessmanager.exception;

public class VerificationTokenNotFoundException extends RuntimeException{

    public VerificationTokenNotFoundException()
    {
        super("Verification token was not found");
    }

    public VerificationTokenNotFoundException(String msg)
    {
        super(msg);
    }
}
