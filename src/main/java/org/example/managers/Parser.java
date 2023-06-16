package org.example.managers;

import org.example.exceptions.NoAccessToTheFileException;
import org.example.exceptions.TroubleObjectCreationException;
import org.example.exceptions.UnknownCommandException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для обработки вводимых команд
 */
public class Parser {
    private QueriesHandler queriesHandler;
    private FileHandler fileHandler;
    private CollectionHandler collectionHandler;
    private CommandHandler commandHandler;

    /**
     *
     * @param queriesHandler объект queriesHandler
     * @param fileHandler объект fileHandler
     * @param collectionHandler объект collectionHandler
     * @param commandHandler объект commandHandler
     */
    public Parser(QueriesHandler queriesHandler, FileHandler fileHandler, CollectionHandler collectionHandler, CommandHandler commandHandler) {
        this.queriesHandler = queriesHandler;
        this.fileHandler = fileHandler;
        this.collectionHandler = collectionHandler;
        this.commandHandler = commandHandler;
    }
    /**
     * Запуск парсинга
     */
    public void Active(String[] start_args) {
        start(start_args);
        while (true) {
            String line;
            try {
                line = this.queriesHandler.query("Введите команду: ") + " ";
            } catch (NoSuchElementException e) {
                if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                    continue;
                } else {
                    System.exit(0);
                    continue;
                }
            }
            String command = line.split(" ", 2)[0];
            String args = line.split(" ", 2)[1].trim();
            try {
                this.commandHandler.runCommand(command, args);
                if (this.queriesHandler.getType() == QueriesHandlerType.FILE & (!command.equals("execute_script") | this.queriesHandler.getQueue().size() != 1)) {
                    this.queriesHandler.write(command + ": выполнено");
                } //else {
                    //this.queriesHandler.write("Команда успешно выполнена");
                //}
                //execute_script src/main/java/org/example/files/file.txt
            } catch (UnknownCommandException | FileNotFoundException e) {
                if (this.queriesHandler.getType() == QueriesHandlerType.CONSOLE) {
                    this.queriesHandler.write(e.getLocalizedMessage());
                } else if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    this.queriesHandler.write("Скрипт некорректно записан.");
                    this.queriesHandler.write("Ошибка: " + e.getMessage() + "\nКоманда: " + line);
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                }
            } catch (TroubleObjectCreationException e) {
                if (this.queriesHandler.getType() == QueriesHandlerType.CONSOLE) {
                    this.queriesHandler.write(e.getLocalizedMessage());
                    while (true) {
                        try {
                            this.commandHandler.runCommand(command, args);
                            //this.queriesHandler.write("Команда успешно выполнена");
                            break;
                        } catch (Exception z) {
                            this.queriesHandler.write(z.getLocalizedMessage());
                        }
                    }
                } else if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    this.queriesHandler.write("Скрипт некорректно записан.");
                    this.queriesHandler.write("Ошибка: " + e.getMessage() + "\nКоманда: " + line);
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                }
            } catch (Exception e) {
                if (this.queriesHandler.getType() == QueriesHandlerType.FILE) {
                    this.queriesHandler.write("Скрипт некорректно записан.");
                    this.queriesHandler.write("Ошибка: " + e.getMessage() + "\nКоманда: " + line);
                    if (this.queriesHandler.getQueue().size() > 1) {
                        this.queriesHandler.setOldScanner();
                    } else {
                        this.queriesHandler.setConsoleType();
                    }
                }
            }
        }
    }

    public void start(String[] args) {
        if (args.length == 0) {
            this.queriesHandler.write("Аргумент передан не был, коллекция будет пустой.");
        } else if (args.length != 1) {
            this.queriesHandler.write("Аргумент командной строки должен быть один.");
        } else {
            String path = args[0];
            try {
                // src/main/java/org/example/files/db.xml
                this.fileHandler.deserial(path);
                this.collectionHandler.setPathToFile(path);
                this.queriesHandler.write("Коллекция десериализована");
            } catch (FileNotFoundException e) {
                this.queriesHandler.write(e.getLocalizedMessage());
            } catch (NoAccessToTheFileException e) {
                this.queriesHandler.write(e.getLocalizedMessage());
                this.collectionHandler.setPathToFile(path);
            }
        }

        try {
            this.commandHandler.runCommand("help", "");
        } catch (UnknownCommandException | TroubleObjectCreationException | FileNotFoundException e) {
            this.queriesHandler.write(e.getLocalizedMessage());
        }
    }
}
