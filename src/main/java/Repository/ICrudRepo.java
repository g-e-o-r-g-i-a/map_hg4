package Repository;

import java.io.IOException;
import java.util.List;
import Exception.Exception2;
import Exception.Exception1;

public interface ICrudRepo<T> {


    T create(T obj) throws IOException, Exception1;

    List<T> getAll();


    T update(T obj) throws IOException, Exception2;


    boolean delete(Long objID) throws IllegalAccessException, IOException;

}