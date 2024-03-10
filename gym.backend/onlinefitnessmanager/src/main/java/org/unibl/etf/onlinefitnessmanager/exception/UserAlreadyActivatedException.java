package org.unibl.etf.onlinefitnessmanager.exception;


public class UserAlreadyActivatedException extends RuntimeException{

    public UserAlreadyActivatedException()
    {
        super("This user is already verified.");
    }

    public UserAlreadyActivatedException(String message)
    {
        super(message);
    }



}

