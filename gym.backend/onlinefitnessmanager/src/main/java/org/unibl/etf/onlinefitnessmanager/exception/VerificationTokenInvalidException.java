package org.unibl.etf.onlinefitnessmanager.exception;

public class VerificationTokenInvalidException extends RuntimeException{

    public VerificationTokenInvalidException()
    {
        super("This verification token was invalidated.");
    }

    public VerificationTokenInvalidException(String message)
    {
        super(message);
    }


}
