package ru.cft.shift.intensive.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.cft.shift.intensive.exception.account.UserAlreadyExistsException;
import ru.cft.shift.intensive.exception.account.UserNotFoundException;
import ru.cft.shift.intensive.exception.event.EventAlreadyExistException;
import ru.cft.shift.intensive.exception.event.EventNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Обработка исключений API
 */
@RestControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        Map<String, Object> body = body(status, ex);
        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.CONFLICT, ex);
    }


    protected Map<String, Object> body(HttpStatus status, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", status.value());
        map.put("message", exception.getMessage());
        return map;
    }

    protected ResponseEntity<Map<String, Object>> handleCustomException(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status).body(body(status, exception));
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("2", "Событие не найдено"));
    }

    @ExceptionHandler(EventAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEventAlreadyExistException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("3", "Данное время уже занято другим событием"));
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
