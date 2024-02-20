package org.unibl.etf.onlinefitnessmanager.exception;

public class FitnessTypeAlreadyExistsException extends RuntimeException{

    public FitnessTypeAlreadyExistsException()
    {
        super("That fitness type already exists in the database.");
    }

    public FitnessTypeAlreadyExistsException(String message)
    {
        super(message);
    }



}
