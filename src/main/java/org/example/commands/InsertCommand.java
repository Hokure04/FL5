package org.example.commands;

import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды добавления нового элемента по заданному ключу
 */
public class InsertCommand extends Command {
    /**
     * @param realization объект realization
     */
    public InsertCommand(Realization realization) {
        super("insert null {element}", "Добавление нового элемента с заданным ключом. Ключ является положительным целым числом.", 1, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException {
        this.getRealization().insert(line, this.getCountArgs());
    }
}
