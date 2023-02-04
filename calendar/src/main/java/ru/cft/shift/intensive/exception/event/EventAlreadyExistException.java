package ru.cft.shift.intensive.exception.event;

public class EventAlreadyExistException extends RuntimeException {
    public EventAlreadyExistException(String message) {
        super(message);
    }
}
