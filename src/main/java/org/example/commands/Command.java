package org.example.commands;

import org.example.exceptions.NoAccessToTheFileException;
import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;

import java.io.FileNotFoundException;
/**
 * Класс базовой команды
 */
abstract public class Command {
    private String description;
    private String name;
    private int countArgs;
    private Realization realization;

    /**
     * @param name название команды
     * @param description описание команды
     * @param countArgs число аргументов команды
     * @param realization объект realization
     */
    public Command(String name, String description, int countArgs, Realization realization) {
        this.name = name;
        this.description = description;
        this.countArgs = countArgs;
        this.realization = realization;
    }
    /**
     * Получить описание команды
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Получить название команды
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получить число аргументов
     */
    public int getCountArgs() {
        return this.countArgs;
    }

    /**
     * Получить объект класса реализаций
     */
    public Realization getRealization() {
        return realization;
    }

    /**
     * Выполнить команду
     * @param line строка с аргументами
     */
    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException, FileNotFoundException, NoAccessToTheFileException {
    }

}