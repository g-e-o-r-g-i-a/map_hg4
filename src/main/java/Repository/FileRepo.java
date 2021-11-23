package Repository;


import java.io.IOException;
import java.util.List;

public interface FileRepo<T> extends ICrudRepo<T>{


    List<T> readFromFile() throws IOException;

    void writeToFile() throws IOException;
    T findOne(Long id) throws IOException;
}