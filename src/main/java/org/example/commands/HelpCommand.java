package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды вывода справки по командам
 */
public class HelpCommand extends Command {
    /**
     * @param realization объект realization
     */
    public HelpCommand(Realization realization) {
        super("help", "Вывод справки по командам.", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().help(line, this.getCountArgs());
    }
}
