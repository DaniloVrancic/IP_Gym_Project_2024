package org.unibl.etf.onlinefitnessmanager.exception;

public class FitnessProgramNotFoundException extends RuntimeException {

    public FitnessProgramNotFoundException()
    {
        super(FitnessProgramNotFoundException.class.getName() + ": The requested Fitness Program was not found!");
    }

    public FitnessProgramNotFoundException(String message)
    {
        super(message);
    }
}
