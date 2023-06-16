package org.example.managers;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.example.data.Vehicle;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.XStream;
import org.example.exceptions.NoAccessToTheFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Класс для сохранения и загрузки коллекции
 */
public class FileHandler {

    private final CollectionHandler<Vehicle> collectionHandler;
    private final XStream xStream;

    /**
     *
     * @param collectionHandler объект collectionHandler
     */
    public FileHandler(CollectionHandler<Vehicle> collectionHandler) {
        this.collectionHandler = collectionHandler;
        this.xStream = new XStream();

        this.xStream.addPermission(NoTypePermission.NONE);
        this.xStream.addPermission(NullPermission.NULL);
        this.xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        this.xStream.addPermission(AnyTypePermission.ANY);

        this.xStream.processAnnotations(Vehicle.class);
        this.xStream.alias("map", LinkedHashMap.class);
        //this.xStream.alias("vehicle", Vehicle.class);
    }

    /**
     * Сериализация коллекции
     */
    public void serial() throws NoAccessToTheFileException, FileNotFoundException {
        String filePath = new File("").getAbsolutePath() + "/";
        File file;
        if (String.valueOf(this.collectionHandler.getPathToFile().charAt(0)).equals("/")) {
            file = new File(this.collectionHandler.getPathToFile());
        } else {
            file = new File(filePath + this.collectionHandler.getPathToFile());
        }
        try {
            if (!file.isFile()) throw new FileNotFoundException("Файла, куда коллекция должна быть сохранена, не существует.");
            if (!file.canWrite()) {
                throw new NoAccessToTheFileException("У программы нет права на запись в файл, коллекцию нельзя сохранить");
            }

            FileWriter writer = new FileWriter(file);
            String data = this.xStream.toXML(this.collectionHandler.getCollection());
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("sus");
        }
    }
    /**
     * Десериализация коллекции
     * @param path путь до файла
     */
    public void deserial(String path) throws FileNotFoundException, NoAccessToTheFileException {
        String filePath = new File("").getAbsolutePath() + "/";
        File testFile;
        if (String.valueOf(path.charAt(0)).equals("/")) {
            testFile = new File(path);
        } else {
            testFile = new File(filePath + path);
        }
        if (!testFile.isFile()) throw new FileNotFoundException("Файла по такому пути нет, коллекция будет пустой");
        if (!testFile.canRead()) {
            throw new NoAccessToTheFileException("У программы нет права на чтение из файла, коллекция не будет десериализована");
        }

        FileInputStream file = new FileInputStream(testFile);
        BufferedInputStream stream = new BufferedInputStream(file, 200);
        @SuppressWarnings("unchecked")
        LinkedHashMap<Integer, Vehicle> ma = (LinkedHashMap<Integer, Vehicle>) this.xStream.fromXML(stream);
        Integer maId = 0;
        ArrayList<Integer> ids = new ArrayList<>();
        for (Vehicle i : ma.values()) {
            if (ids.contains(i.getId())) {
                throw new FileNotFoundException("Файл поврежден, в нем есть повторяющиеся id, коллекция не будет десериализована");
            }
            ids.add(i.getId());
            if (i.getId() > maId) {
                maId = i.getId();
            }
        }
        Vehicle.init(maId);
        this.collectionHandler.setCollection(ma);
    }
}