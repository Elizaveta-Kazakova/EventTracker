package ru.cft.shift.intensive.exception.account;

/**
 * Исключение, выбрасываемое при добавлении уже существующего пользователя
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
