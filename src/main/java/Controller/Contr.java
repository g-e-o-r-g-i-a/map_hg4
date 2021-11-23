package Controller;

import java.io.IOException;
import java.util.List;
import Exception.Exception1;
import Exception.Exception2;
public interface Contr<T> {


    T create(T obj) throws IOException,Exception1;

    List<T> getAll();

    T update(T obj) throws IOException, Exception2;

    boolean delete(Long objID) throws IllegalAccessException, IOException;

    List<T> readFromFile() throws IOException;

    void writeToFile() throws IOException;

    T findOne(Long id) throws IOException;
}