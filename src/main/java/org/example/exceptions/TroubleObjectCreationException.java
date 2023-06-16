package org.example.exceptions;
/**
 * Класс ошибки создания объекта класса
 */
public class TroubleObjectCreationException extends Exception{
    public TroubleObjectCreationException(String message) {
        super(message);
    }
}
