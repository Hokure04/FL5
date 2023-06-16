package org.example;

import org.example.commands.*;
import org.example.data.Vehicle;
import org.example.managers.*;
/**
 * Стартовый класс
 */
public class Main {
    /**
     * Стартовый метод
     */
    public static void main(String[] args) {

        CollectionHandler<Vehicle> collectionHandler= new CollectionHandler<Vehicle>();
        collectionHandler.setPathToFile("src/main/java/org/example/files/db.xml");
        QueriesHandler queriesHandler = new QueriesHandler();
        CommandHandler commandHandler = new CommandHandler();
        FileHandler fileHandler = new FileHandler(collectionHandler);

        Realization realization = new Realization(collectionHandler, queriesHandler, commandHandler, fileHandler);

        commandHandler.addCommand("help", new HelpCommand(realization));
        commandHandler.addCommand("info", new InfoCommand(realization));
        commandHandler.addCommand("show", new ShowCommand(realization));
        commandHandler.addCommand("insert", new InsertCommand(realization));
        commandHandler.addCommand("update", new UpdateCommand(realization));
        commandHandler.addCommand("remove_key", new RemoveKeyCommand(realization));
        commandHandler.addCommand("clear", new ClearCommand(realization));
        commandHandler.addCommand("save", new SaveCommand(realization));
        commandHandler.addCommand("execute_script", new ExecuteScriptCommand(realization));
        commandHandler.addCommand("exit", new ExitCommand(realization));
        commandHandler.addCommand("remove_greater", new RemoveGreaterCommand(realization));
        commandHandler.addCommand("replace_if_lower", new ReplaceIfLowerCommand(realization));
        commandHandler.addCommand("remove_lower_key", new RemoveLowerKeyCommand(realization));
        commandHandler.addCommand("group_counting_by_id", new GroupCountingByIdCommand(realization));
        commandHandler.addCommand("filter_greater_than_fuel_type", new FilterGreaterThanFuelTypeCommand(realization));
        commandHandler.addCommand("print_descending", new PrintDescendingCommand(realization));

        Parser receiveHandler = new Parser(queriesHandler, fileHandler, collectionHandler, commandHandler);
        receiveHandler.Active(args);
    }
}