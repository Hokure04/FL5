package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды удаления всех элементов в коллекции, чей ключ меньше заданного
 */
public class RemoveLowerKeyCommand extends Command {
    /**
     * @param realization объект realization
     */
    public RemoveLowerKeyCommand(Realization realization) {
        super("remove_lower_key null", "Удаление из коллекции всех элементов, чей ключ меньше заданного. Ключ является положительным целым числом.", 1, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().removeLowerKey(line, this.getCountArgs());
    }
}
