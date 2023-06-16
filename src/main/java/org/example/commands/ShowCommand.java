package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды вывода всех элементов коллекции
 */
public class ShowCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ShowCommand(Realization realization) {
        super("show", "Вывод всех элементов коллекции.", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().show(line, this.getCountArgs());
    }
}
