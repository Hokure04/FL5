package org.example.managers;

import org.example.exceptions.NoAccessToTheFileException;
import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
import org.example.commands.Command;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс для обработки команд
 */
public class CommandHandler {
    private final Map<String, Command> commands;

    public CommandHandler() {
        this.commands = new LinkedHashMap<String, Command>();
    }
    /**
     * Добавить новую команду
     * @param name название команды
     * @param command объект команды
     */
    public void addCommand(String name, Command command) {
        this.commands.put(name, command);
    }
    /**
     * Вызов команды
     * @param command название команды
     * @param args аргументы команды
     */
    public void runCommand(String command, String args) throws UnknownCommandException, TroubleObjectCreationException, FileNotFoundException {
        if (!this.commands.containsKey(command)) throw new UnknownCommandException("Ошибка: несуществующая команда");
        try {
            this.commands.get(command).execute(args);
            //System.out.println(this.builders.get(command).);
        } catch (TroubleObjectCreationException e) {
            throw new TroubleObjectCreationException(e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getLocalizedMessage());
        } catch (NoAccessToTheFileException e) {
            throw new FileNotFoundException(e.getLocalizedMessage());
        }
    }

    public Map<String, Command> getCommands() {
        return this.commands;
    }
}