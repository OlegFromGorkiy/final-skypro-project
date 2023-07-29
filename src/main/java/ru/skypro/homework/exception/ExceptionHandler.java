package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Компонент который перехватывает исключения при работе контроллера и формирует ответы согласно перехваченных исключений
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbiddenException(ForbiddenException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundElement.class)
    public ResponseEntity<String> notFoundElement(NotFoundElement exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ReadOrWriteException.class)
    public ResponseEntity<String> readOrWriteException(ReadOrWriteException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
