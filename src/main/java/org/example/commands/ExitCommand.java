package org.example.commands;

import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды выхода из программы
 */
public class ExitCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ExitCommand(Realization realization) {
        super("exit", "Завершение программы без сохранения коллекции в файл", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException {
        this.getRealization().exit(line, this.getCountArgs());
    }
}
