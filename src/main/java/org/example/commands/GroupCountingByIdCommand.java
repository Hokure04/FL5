package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды группировки объектов коллекции по первой цифре ID
 */
public class GroupCountingByIdCommand extends Command {
    /**
     * @param realization объект realization
     */
    public GroupCountingByIdCommand(Realization realization) {
        super("group_counting_by_id", "Группировка всех элементов коллекции по значению поля id и вывод количества элементов в каждой группе.", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().groupCountingById(line, this.getCountArgs());
    }
}
