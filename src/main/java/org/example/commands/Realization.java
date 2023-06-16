package org.example.commands;

import org.example.data.FuelType;
import org.example.data.Vehicle;
import org.example.exceptions.NoAccessToTheFileException;
import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;
import org.example.managers.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;
/**
 * Класс реализаций команд(receiver)
 */
public class Realization {

    private CollectionHandler<Vehicle> collectionHandler;
    private QueriesHandler queriesHandler;
    private CommandHandler commandHandler;
    private FileHandler fileHandler;
    private Vehicle.VehicleBuilder elementBuilder;

    /**
     * @param collectionHandler объект collectionHandler
     * @param queriesHandler объект queriesHandler
     * @param commandHandler объект commandHandler
     * @param fileHandler объект fileHandler
     */
    public Realization(CollectionHandler<Vehicle> collectionHandler, QueriesHandler queriesHandler, CommandHandler commandHandler, FileHandler fileHandler) {
        this.collectionHandler = collectionHandler;
        this.queriesHandler = queriesHandler;
        this.commandHandler = commandHandler;
        this.fileHandler = fileHandler;
        this.elementBuilder = new Vehicle.VehicleBuilder(queriesHandler);
    }
    /**
     * Реализация команды clear
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void clear(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        this.collectionHandler.setNewCollection();
    }
    /**
     * Реализация команды executeScript
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void executeScript(String line, int countArgs) throws UnknownCommandException, FileNotFoundException, NoAccessToTheFileException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

//        if (this.queriesHandler.getType() == QueriesHandlerType.CONSOLE) {
//            this.queriesHandler.setFileType(line);
//        } else if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
//            throw new UnknownCommandException("executeScript нельзя вызвать из файла");
//        }
        this.queriesHandler.setFileType(line);
    }
    /**
     * Реализация команды exit
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void exit(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        System.exit(0);
    }
    /**
     * Реализация команды filterGreaterThanFuelType
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void filterGreaterThanFuelType(String line, int countArgs) throws UnknownCommandException, TroubleObjectCreationException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        String var = "Возможные значения:";
        for (FuelType i : FuelType.values()) {
            var += " " + i.name();
        }
        String rowFuelType = this.queriesHandler.query("Введите тип топлива. Значение должно быть одной из заранее определенных констант.\n"+var);

        try {
            Validator.validateEnum(rowFuelType, FuelType.class);
        } catch (UnknownCommandException e) {
            //System.out.println(e.getLocalizedMessage());
            throw new TroubleObjectCreationException(e.getLocalizedMessage());
            //filterGreaterThanFuelType(line, countArgs);
            //return;
        }

        FuelType fuelType = FuelType.valueOf(rowFuelType.toUpperCase());

        String[] names = new String[]{"Id", "Name", "Coordinates", "Creation date", "Engine power", "Type", "Fuel type"};

        Integer[] widths = createWidth(names);

        ArrayList<String> response = new ArrayList<String>();
        Comparator<FuelType> comparator = Enum::compareTo;
        //for (Vehicle i : this.collectionHandler.getCollection().values()) {
        for (Vehicle i : this.collectionHandler.getCollection().values()) {
            if (comparator.compare(i.getFuelType(), fuelType) > 0) {
                String[] el = i.getAll();
                for (int z = 0; z <= 6; z++) {
                    if (el[z].length() + 10 > widths[z + 1]) {
                        widths[z] = el[z].length() + 10;
                    }
                }
            }
        }
        String s = "";
        for (int i = 0; i <= 6; i++) {
            s += names[i] + func(widths[i] - names[i].length());
        }

        this.queriesHandler.writeCommand("Выведены элементы, у которых FuelType больше заданного");
        this.queriesHandler.writeCommand(s);
        this.queriesHandler.writeCommand("");

        for (Vehicle i : this.collectionHandler.getCollection().values()) {
            if (comparator.compare(i.getFuelType(), fuelType) > 0) {
                s = "";
                String[] el = i.getAll();
                for (int z = 0; z <= 6; z++) {
                    s += el[z] + func(widths[z] - el[z].length());
                }
                this.queriesHandler.writeCommand(s);
            }
        }

        this.queriesHandler.writeCommand("Результат: " + String.join(", ", response));
        //System.out.println("Результат: " + String.join(", ", response));
    }
    /**
     * Реализация команды groupCountingById
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void groupCountingById(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        Map<Integer, ArrayList<String>> map = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, new ArrayList<String>());
        }
        for (Vehicle i : this.collectionHandler.getCollection().values()) {
            String fir = String.valueOf(i.getId().toString().charAt(0));
            map.get(Integer.parseInt(fir)).add(i.getName());
        }
        this.queriesHandler.writeCommand("Группировка производится по первой цифре ID");
        for (int i : map.keySet()) {
            if (map.get(i).size() != 0) {
                this.queriesHandler.writeCommand(i+": "+map.get(i).size());
                //System.out.println(i+": "+String.join(", ", map.get(i)));
            }
        }
    }
    /**
     * Реализация команды help
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void help(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        for (Command i : this.commandHandler.getCommands().values()) {
            String name = i.getName();
            String description = i.getDescription();
            this.queriesHandler.writeCommand(name + ": " + description);
            //System.out.println(name + ": " + description);
        }
    }
    /**
     * Реализация команды info
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void info(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        this.queriesHandler.writeCommand("Тип: " + this.collectionHandler.getCollection().getClass().getSimpleName());
        this.queriesHandler.writeCommand("Дата инициализации: " + this.collectionHandler.getCreateDate());
        this.queriesHandler.writeCommand("Число элементов: " + this.collectionHandler.getCollection().size());
        this.queriesHandler.writeCommand("Путь до файла базы данных: " + this.collectionHandler.getPathToFile());

//        System.out.println("Тип: " + this.collectionHandler.getCollection().getClass().getSimpleName());
//        System.out.println("Дата инициализации: " + this.collectionHandler.getCreateDate());
//        System.out.println("Число элементов: " + this.collectionHandler.getCollection().size());
//        System.out.println("Путь до файла базы данных: " + this.collectionHandler.getPathToFile());
    }
    /**
     * Реализация команды insert
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void insert(String line, int countArgs) throws UnknownCommandException, TroubleObjectCreationException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);
        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        if (this.collectionHandler.getCollection().get(key) != null) {
            throw new UnknownCommandException("Элемент с таким ключем уже существует");
            //System.out.println("Элемент с таким ключем уже существует");
        } else {
            Vehicle element = this.elementBuilder.build();
            this.collectionHandler.addElement(key, element);
        }
    }
    /**
     * Реализация команды printDescending
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void printDescending(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        String response = "";

        String[] names = new String[]{"Id", "Name", "Coordinates", "Creation date", "Engine power", "Type", "Fuel type"};

        Integer[] widths = createWidth(names);

        for (Vehicle i : this.collectionHandler.getCollection().values().stream().sorted(Collections.reverseOrder()).toList()) {
            String[] el = i.getAll();
            for (int z = 0; z <= 6; z++) {
                if (el[z].length() + 10 > widths[z + 1]) {
                    widths[z] = el[z].length() + 10;
                }
            }
        }
        String s = "";
        for (int i = 0; i <= 6; i++) {
            s += names[i] + func(widths[i] - names[i].length());
        }

        this.queriesHandler.writeCommand("Элементы выведены сверху вниз в порядке убывания Engine power");
        this.queriesHandler.writeCommand(s);
        this.queriesHandler.writeCommand("");

        for (Vehicle i : this.collectionHandler.getCollection().values().stream().sorted(Collections.reverseOrder()).toList()) {
            s = "";
            String[] el = i.getAll();
            for (int z = 0; z <= 6; z++) {
                s += el[z] + func(widths[z] - el[z].length());
            }
            this.queriesHandler.writeCommand(s);
        }

        //Stream<String> arr = this.collectionHandler.getCollection().values().stream().sorted(Collections.reverseOrder()).map(Vehicle::getName);
        //response = String.join(" > ", arr.toList());
        //this.queriesHandler.writeCommand(response);
//        System.out.println(response);
    }
    /**
     * Реализация команды removeGreater
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void removeGreater(String line, int countArgs) throws UnknownCommandException, TroubleObjectCreationException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        Vehicle element = this.elementBuilder.build();
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i : this.collectionHandler.getCollection().keySet()) {
            if (this.collectionHandler.getCollection().get(i).compareTo(element) > 0) {
                keys.add(i);
            }
        }
        for (int i : keys) {
            this.collectionHandler.removeElement(i);
        }
    }
    /**
     * Реализация команды removeKey
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void removeKey(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);
        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        if (this.collectionHandler.getCollection().containsKey(key)) {
            this.collectionHandler.removeElement(key);
        } else {
            throw new UnknownCommandException("Элемента под таким ключем нет.");
            //System.out.println("Элемента под таким ключем нет.");
        }
    }
    /**
     * Реализация команды removeLowerKey
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void removeLowerKey(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);
        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i : this.collectionHandler.getCollection().keySet()) {
            if (i < key) {
                keys.add(i);
            }
        }
        for (int i : keys) {
            this.collectionHandler.removeElement(i);
        }
    }
    /**
     * Реализация команды replaceIfLower
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void replaceIfLower(String line, int countArgs) throws UnknownCommandException, TroubleObjectCreationException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);
        Validator.validateKey(args[0]);

        int key = Integer.parseInt(args[0]);
        if (this.collectionHandler.getCollection().containsKey(key)) {
            Vehicle element = this.elementBuilder.build();
            if (this.collectionHandler.getCollection().get(key).compareTo(element) > 0) {
                this.collectionHandler.replaceElement(key,element);
            }
        } else {
            throw new UnknownCommandException("Элемента под указанным ключом нет.");
            //System.out.println("Элемента под указанным ключом нет.");
        }
    }
    /**
     * Реализация команды save
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void save(String line, int countArgs) throws UnknownCommandException, NoAccessToTheFileException, FileNotFoundException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        this.fileHandler.serial();
    }
    /**
     * Реализация команды show
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void show(String line, int countArgs) throws UnknownCommandException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);

        String[] names = new String[]{"Key", "Id", "Name", "Coordinates", "Creation date", "Engine power", "Type", "Fuel type"};

        Integer[] widths = createWidth(names);

        for (Integer z : this.collectionHandler.getCollection().keySet()) {
            if (z.toString().length() > widths[0]) {
                widths[0] = z.toString().length() + 10;
            }
            String[] el = this.collectionHandler.getCollection().get(z).getAll();
            for (int i = 0; i <= 6; i++) {
                if (el[i].length() + 10 > widths[i + 1]) {
                    widths[i + 1] = el[i].length() + 10;
                }
            }
        }
        String s = "";
        for (int i = 0; i <= 7; i++) {
            s += names[i] + func(widths[i] - names[i].length());
        }
        this.queriesHandler.writeCommand(s);
        this.queriesHandler.writeCommand("");
        //System.out.println(s);
        //System.out.println("");
        for (Integer z : this.collectionHandler.getCollection().keySet()) {
            s = "";
            s += z + func(widths[0]-z.toString().length());
            String[] el = this.collectionHandler.getCollection().get(z).getAll();
            for (int i = 0; i <= 6; i++) {
                s += el[i] + func(widths[i+1] - el[i].length());
            }
            this.queriesHandler.writeCommand(s);
            //System.out.println(s);
        }
    }

    public Integer[] createWidth(String[] names) {
        Integer[] widths = new Integer[]{0,0,0,0,0,0,0,0};

        for (int i = 0; i < names.length; i++) {
            if (names[i].length() + 10 > widths[i]) {
                widths[i] = names[i].length() + 10;
            }
        }

        return widths;
    }

    private String func(int a) {
        String s = "";
        for (int i = 0; i < a; i++) {
            s += " ";
        }
        //System.out.println("1"+s+"1");
        return s;
    }
    /**
     * Реализация команды update
     * @param line строка с аргументами
     * @param countArgs число аргументов
     */
    public void update(String line, int countArgs) throws UnknownCommandException, TroubleObjectCreationException {
        String[] args = line.split(" +", 2);
        Validator.validateCountArgs(args, countArgs);
        Validator.validateId(args[0]);

        int id = Integer.parseInt(args[0]);
        for (int i : this.collectionHandler.getCollection().keySet()) {
            if (this.collectionHandler.getCollection().get(i).getId() == id) {
                Vehicle element = this.elementBuilder.build(id);
                this.collectionHandler.replaceElement(i, element);
                return;
            }
        }
        throw new UnknownCommandException("Элемента с таким id нет в коллекции");
        //System.out.println("Элемента с таким id нет в коллекции");
    }
}
