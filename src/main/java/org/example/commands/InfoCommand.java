package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды вывода информации о коллекции
 */
public class InfoCommand extends Command {
    /**
     * @param realization объект realization
     */
    public InfoCommand(Realization realization) {
        super("info", "Вывод информации о коллекции.", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().info(line, this.getCountArgs());
    }
}
