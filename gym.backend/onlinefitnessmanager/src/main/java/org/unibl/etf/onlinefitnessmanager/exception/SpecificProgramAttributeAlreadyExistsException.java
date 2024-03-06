package org.unibl.etf.onlinefitnessmanager.exception;

public class SpecificProgramAttributeAlreadyExistsException extends RuntimeException{

    public SpecificProgramAttributeAlreadyExistsException()
    {
        super("That fitness type already exists in the database.");
    }

    public SpecificProgramAttributeAlreadyExistsException(String message)
    {
        super(message);
    }
}
