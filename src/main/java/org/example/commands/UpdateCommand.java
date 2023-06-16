package org.example.commands;

import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды замена объекта по id
 */
public class UpdateCommand extends Command {
    /**
     * @param realization объект realization
     */
    public UpdateCommand(Realization realization) {
        super("update id {element}", "Замена объекта с указанным id. id является положительным целым числом.", 1, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException {
        this.getRealization().update(line, this.getCountArgs());
    }
}
