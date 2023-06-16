package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды очистки коллекции
 */
public class ClearCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ClearCommand(Realization realization) {
        super("cleat", "Очистка коллекции.", 0, realization);
    }
    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().clear(line, this.getCountArgs());
    }
}
