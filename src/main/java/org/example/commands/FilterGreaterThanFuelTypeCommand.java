package org.example.commands;

import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
/**
 * Класс команды вывода элементов коллекции, больших заданных по типу топлива
 */
public class FilterGreaterThanFuelTypeCommand extends Command {
    /**
     * @param realization объект realization
     */
    public FilterGreaterThanFuelTypeCommand(Realization realization) {
        super("filter_greater_than_fuel_type fuelType", "Вывод элементов, значение fuelType которых больше заданного. fuelType является одной из заранее определенных констант", 0, realization);
    }

    @Override
    public void execute(String line) throws UnknownCommandException, TroubleObjectCreationException {
        this.getRealization().filterGreaterThanFuelType(line, this.getCountArgs());
    }
}
