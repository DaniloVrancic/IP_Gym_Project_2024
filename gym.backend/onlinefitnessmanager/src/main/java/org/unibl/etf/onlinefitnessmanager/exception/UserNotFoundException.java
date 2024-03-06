package org.unibl.etf.onlinefitnessmanager.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException()
    {
        super("Requested user has not been found in database.");
    }

    public UserNotFoundException(String message)
    {
        super(message);
    }



}
