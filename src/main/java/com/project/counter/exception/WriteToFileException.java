package com.project.counter.exception;

public class WriteToFileException extends Exception {
    public WriteToFileException() {
    }

    public WriteToFileException(String message) {
        super(message);
    }

    public WriteToFileException(Throwable cause) {
        super(cause);
    }

    public WriteToFileException(String message, Throwable cause) {
        super(message, cause);
    }


}
