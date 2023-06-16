package org.example.commands;

import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды замены элемента по ключу, если новый объект меньше старого
 */
public class ReplaceIfLowerCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ReplaceIfLowerCommand(Realization realization) {
        super("replace_if_lower null {element}", "Замена элемента на заданный по заданному ключу в случае, если значение enginePower заданного объекта меньше, чем у нынешнего. Ключ является положительным целым числом.", 1, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException {
        this.getRealization().replaceIfLower(line, this.getCountArgs());
    }
}
