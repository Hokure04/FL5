package org.example.managers;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс для работы с коллекцией
 */
public class CollectionHandler<T> {
    private Map<Integer, T> collection;
    private final LocalDate createDate;

    private String pathToFile;
    public CollectionHandler() {
        this.collection = new LinkedHashMap<Integer, T>();
        this.createDate = LocalDate.now();
    }
    /**
     * Получить дату создания коллекции
     */
    public LocalDate getCreateDate() {
        return createDate;
    }
    /**
     * Получить ссылку на коллекцию
     */
    public Map<Integer, T> getCollection() {
        return this.collection;
    }

    /**
     * Пересоздать коллекцию
     */
    public void setNewCollection() {
        this.collection = new LinkedHashMap<Integer, T>();
    }
    /**
     * Установить путь до файла, куда будет сохраняться коллекция
     * @param path Путь до файла
     */
    public void setPathToFile(String path) {
        this.pathToFile = path;
    }
    /**
     * Получить путь до файла, куда будет сохраняться коллекция
     */
    public String getPathToFile() {
        return this.pathToFile;
    }
    /**
     * Заполнить коллекцию
     * @param col коллекция, которая будет записана
     */
    public void setCollection(LinkedHashMap<Integer, T> col) {
        this.collection = col;
    }
    /**
     * Добавить элемент в коллекцию по ключу
     * @param key ключ нового элемента
     * @param element объект нового элемента
     */
    public void addElement(int key, T element) {
        this.collection.put(key, element);
    }
    /**
     * Удалить элемент из коллекции по ключу
     * @param key ключ удаляемого элемента
     */
    public void removeElement(int key) {
        this.collection.remove(key);
    }
    /**
     * Заменить объект коллекции по ключу
     * @param key ключ заменяемого элемента
     * @param element объект нового элемента
     */
    public void replaceElement(int key, T element) {
        this.collection.replace(key, element);
    }
    /**
     * Создать Id для нового элемента коллекции
     */
}
