package org.example.data;

import org.example.exceptions.EmptyValueException;
import org.example.exceptions.TroubleObjectCreationException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.example.managers.QueriesHandler;

import java.time.LocalDate;
/**
 * Класс транспорта
 */
@XStreamAlias("vehicle")
public class Vehicle implements Comparable<Vehicle> {
    private static Integer maxId = 0;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long enginePower; //Поле может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле не может быть null

    /**
     *
     * @param name название
     * @param coordinates объект координат
     * @param enginePower мощность
     * @param type тип транспорта
     * @param fuelType тип топлива
     */
    private Vehicle(String name, Coordinates coordinates, Long enginePower, VehicleType type, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
        this.id = maxId+1;
        maxId += 1;
    }

    private Vehicle(Integer id, String name, Coordinates coordinates, Long enginePower, VehicleType type, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.type = type;
        this.fuelType = fuelType;
        this.id = id;
    }
    public static void init(Integer id) {
        maxId = id;
    }
    /**
     * Получить ID
     */
    public Integer getId() {
        return this.id;
    }
    /**
     * Получить мощность
     */
    public Long getEnginePower() {
        return enginePower;
    }
    /**
     * Получить тип топлива
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Получить координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Получить дату создания
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Получить тип транспортного средства
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Получить название
     */
    public String getName() {
        return name;
    }

    /**
     * Получить все поля объекта
     */
    public String[] getAll() {
        String[] a = new String[7];
        a[0] = this.getId().toString();
        a[1] = this.getName();
        a[2] = this.getCoordinates().toString();
        a[3] = this.getCreationDate().toString();
        a[4] = this.getEnginePower().toString();
        a[5] = this.getType().toString();
        a[6] = this.getFuelType().toString();
        return a;
    }

    @Override
    public String toString() {
        return String.format("id - %s; name - %s; coordinates - %s; creationDate - %s; " +
                "enginePower - %s; type - %s; fuelType - %s", this.id.toString(), this.name, this.coordinates.toString(),
                this.creationDate.toString(), this.enginePower.toString(), this.type.toString(), this.fuelType.toString());
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return (int) (this.enginePower - vehicle.getEnginePower());
    }
    /**
     * Класс-билдер транспорта
     */
    public static class VehicleBuilder {
        private final QueriesHandler queriesHandler;
        private String name = null;
        private Coordinates.CoordinatesBuilder coordinatesBuilder = null;
        private Coordinates coordinates = null;
        private Long enginePower = null;
        private VehicleType type = null;
        private FuelType fuelType = null;

        /**
         *
         * @param queriesHandler объект queriesHandler
         */
        public VehicleBuilder(QueriesHandler queriesHandler) {
            this.queriesHandler = queriesHandler;
        }

        public void createFields() throws TroubleObjectCreationException {
            try {
                if (this.name == null) {
                    this.name = writeName();
                }
                if (this.coordinatesBuilder == null) {
                    this.coordinatesBuilder = new Coordinates.CoordinatesBuilder(queriesHandler);
                }
                if (this.coordinates == null) {
                    this.coordinates = this.coordinatesBuilder.build();
                }
                if (this.enginePower == null) {
                    this.enginePower = writeEnginePower();
                }
                if (this.type == null) {
                    this.type = writeVehicleType();
                }
                if (this.fuelType == null) {
                    this.fuelType = writeFuelType();
                }
            } catch (Exception e) {
                throw new TroubleObjectCreationException(e.getLocalizedMessage());
            }
        }

        /**
         * Запуск создания
         */
        public Vehicle build() throws TroubleObjectCreationException {
            createFields();
            Vehicle element = new Vehicle(this.name, this.coordinates, this.enginePower, this.type, this.fuelType);
            endBuild();
            return element;
        }
        public Vehicle build(Integer id) throws TroubleObjectCreationException {
            createFields();
            Vehicle element = new Vehicle(id, this.name, this.coordinates, this.enginePower, this.type, this.fuelType);
            endBuild();
            return element;
        }
        /**
         * Запрос названия
         */
        public String writeName() throws EmptyValueException {
            String name = this.queriesHandler.query("Введите имя объекта.");
            if (name.equals("")) throw new EmptyValueException("Ошибка: имя не может быть пустой строкой");
            return name;
        }
        /**
         * Запрос мощности
         */
        public Long writeEnginePower() {
            Long power;
            try {
                power = Long.parseLong(this.queriesHandler.query("Введите мощность двигателя. Значение должно быть целым и положительным."));
                if (power <= 0) {
                    throw new TroubleObjectCreationException("Ошибка: значение мощности двигателя должно быть положительным");
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка: введенное значение не является целым числом, либо превышена максимальная величина целого числа.");
            } catch (TroubleObjectCreationException e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }

            return power;
        }
        /**
         * Запрос типа транспорта
         */
        public VehicleType writeVehicleType() {
            String var = "Возможные значения:";
            for (VehicleType i : VehicleType.values()) {
                var += " " + i.name();
            }
            //System.out.println(var);
            String line = this.queriesHandler.query("Введите тип транспорта. Значение должно быть одной из заранее определенных констант.\n"+var);
            VehicleType type;
            try {
                type = VehicleType.valueOf(line.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ошибка: введенной константы типа транспорта не существует");
            }
            return type;
        }
        /**
         * Запрос типа топлива
         */
        public FuelType writeFuelType() {
            String var = "Возможные значения:";
            for (FuelType i : FuelType.values()) {
                var += " " + i.name();
            }
            //System.out.println(var);
            String line = this.queriesHandler.query("Введите тип топлива. Значение должно быть одной из заранее определенных констант.\n"+var);
            FuelType type;
            try {
                type = FuelType.valueOf(line.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ошибка: введенной константы типа топлива не существует");
            }
            return type;
        }
        /**
         * Зануление полей после создания
         */
        public void endBuild() {
            this.name = null;
            this.coordinatesBuilder = null;
            this.coordinates = null;
            this.enginePower = null;
            this.type = null;
            this.fuelType = null;
        }
    }

}