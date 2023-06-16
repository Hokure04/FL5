package org.example.data;

import org.example.exceptions.TroubleCoordinateCreationException;
import org.example.managers.QueriesHandler;
/**
 * Класс координат
 */
public class Coordinates {
    private double x;
    private Double y; //Поле не может быть null

    /**
     * @param x координата X
     * @param y координата Y
     */
    public Coordinates(double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("x - %s; y - %s", this.x, this.y.toString());
    }
    /**
     * Класс-билдер координат
     */
    public static class CoordinatesBuilder {
        private QueriesHandler queriesHandler;
        private Double x = null;
        private Double y = null;

        /**
         *
         * @param queriesHandler объект queriesHandler
         */
        public CoordinatesBuilder(QueriesHandler queriesHandler) {
            this.queriesHandler = queriesHandler;
        }
        /**
         * Запуск создания
         */
        public Coordinates build() throws TroubleCoordinateCreationException {
            try {
                if (x == null) {
                    this.x = writeX();
                }
                if (y == null) {
                    this.y = writeY();
                }
            } catch (Exception e) {
                throw new TroubleCoordinateCreationException(e.getLocalizedMessage());
            }
            return new Coordinates(x, y);
        }
        /**
         * Запрос координаты X
         */
        public double writeX() {
            Double x;
            try {
                x = Double.parseDouble(this.queriesHandler.query("Введите координату X. Она должна быть вещественным числом."));
                if (x.isInfinite()) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка: введенное число не является вещественным числом, либо превышена максимальная величина целого числа.");
            }
            return x;
        }
        /**
         * Запрос координаты Y
         */
        public Double writeY() {
            Double y;
            try {
                y = Double.parseDouble(this.queriesHandler.query("Введите координату Y. Она должна быть вещественным числом."));
                if (y.isInfinite()) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка: введенное число не является вещественным числом, либо превышена максимальная величина целого числа.");
            }
            return y;
        }
        /**
         * Зануление полей после создания
         */
        public void endBuild() {
            this.x = null;
            this.y = null;
        }
    }
}