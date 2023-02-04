package ru.cft.shift.intensive.exception.account;

/**
 * Исключение, выбрасываемое при отсутвии пользователя в системе
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
