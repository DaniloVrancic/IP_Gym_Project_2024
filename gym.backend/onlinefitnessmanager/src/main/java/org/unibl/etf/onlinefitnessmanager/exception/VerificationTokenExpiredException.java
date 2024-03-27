package org.unibl.etf.onlinefitnessmanager.exception;

public class VerificationTokenExpiredException extends RuntimeException{

    public VerificationTokenExpiredException()
    {
        super("This verification token has expired.");
    }

    public VerificationTokenExpiredException(String message)
    {
        super(message);
    }

}