package org.example.commands;

import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
/**
// * Класс команды удаления из коллекции всех элементов, больших заданного
 */
public class RemoveGreaterCommand extends Command {
    /**
     * @param realization объект realization
     */
    public RemoveGreaterCommand(Realization realization) {
        super("remove_greater {element}", "Удаление из коллекции всех элементов, значение enginePower которых больше, чем у заданного объекта", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException {
        this.getRealization().removeGreater(line, this.getCountArgs());
    }
}
