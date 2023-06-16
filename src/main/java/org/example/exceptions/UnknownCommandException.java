package org.example.exceptions;
/**
 * Класс ошибки неизвестной команды
 */
public class UnknownCommandException extends Exception{
    public UnknownCommandException(String message) {
        super(message);
    }
}
