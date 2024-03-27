package org.unibl.etf.onlinefitnessmanager.exception;

public class VerificationTokenInvalidException extends RuntimeException{

    public VerificationTokenInvalidException()
    {
        super("This verification token is invalid.");
    }

    public VerificationTokenInvalidException(String message)
    {
        super(message);
    }


}
