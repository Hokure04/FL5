package org.example.commands;

import org.example.exceptions.NoAccessToTheFileException;
import org.example.exceptions.UnknownCommandException;

import java.io.FileNotFoundException;
/**
 * Класс команды выполнения команд из файла
 */
public class ExecuteScriptCommand extends Command {
    /**
     * @param realization объект realization
     */
    public ExecuteScriptCommand(Realization realization) {
        super("execute_script file_name", "Исполнение команд из указанного файла. Команды должны быть записаны аналогично тому, как они записываются в консоль.", 1, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, FileNotFoundException, NoAccessToTheFileException {
        this.getRealization().executeScript(line, this.getCountArgs());
    }
}
