package org.example.commands;

import org.example.exceptions.NoAccessToTheFileException;
import org.example.exceptions.UnknownCommandException;

import java.io.FileNotFoundException;

/**
 * Класс команды сохранения коллекции
 */
public class SaveCommand extends Command {
    /**
     * @param realization объект realization
     */
    public SaveCommand(Realization realization) {
        super("save", "Сохранение коллекции в файл", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, NoAccessToTheFileException, FileNotFoundException {
        this.getRealization().save(line, this.getCountArgs());
    }
}
