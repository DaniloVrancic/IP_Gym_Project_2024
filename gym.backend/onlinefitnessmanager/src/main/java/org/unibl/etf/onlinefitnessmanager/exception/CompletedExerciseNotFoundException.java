package org.unibl.etf.onlinefitnessmanager.exception;

public class CompletedExerciseNotFoundException extends RuntimeException{

    public CompletedExerciseNotFoundException()
    {
        super();
    }

    public CompletedExerciseNotFoundException(String msg)
    {
        super(msg);
    }
}
