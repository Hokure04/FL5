package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды удаления элемента по ключу
 */
public class RemoveKeyCommand extends Command {
    /**
     * @param realization объект realization
     */
    public RemoveKeyCommand(Realization realization) {
        super("remove_key null", "Удаление элемента по указанному ключу. Ключ является положительным целым числом.", 1, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().removeKey(line, this.getCountArgs());
    }
}
