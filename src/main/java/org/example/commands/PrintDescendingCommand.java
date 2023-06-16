package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды вывода элементов коллекции в порядке убывания
 */
public class PrintDescendingCommand extends Command {
    /**
     * @param realization объект realization
     */
    public PrintDescendingCommand(Realization realization) {
        super("print_descending", "Вывод элементов коллекции в порядке убывания значения enginePower.", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().printDescending(line, this.getCountArgs());
    }
}
