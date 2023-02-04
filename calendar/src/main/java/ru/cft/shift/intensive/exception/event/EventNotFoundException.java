package ru.cft.shift.intensive.exception.event;

/**
 * Исключение, выбрасываемое при отсутвии события в системе
 */

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
