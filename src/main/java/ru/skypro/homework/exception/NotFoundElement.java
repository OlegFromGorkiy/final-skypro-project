package ru.skypro.homework.exception;

public class NotFoundElement extends RuntimeException {
    public NotFoundElement(long id, Class<?> clazz) {
        super(String.format("%s not found by id=%d", clazz.getSimpleName(), id));
    }
}
