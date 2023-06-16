package org.example.exceptions;
/**
 * Класс ошибки создания координат
 */
public class TroubleCoordinateCreationException extends Exception{
    public TroubleCoordinateCreationException(String message) {
        super(message);
    }
}
