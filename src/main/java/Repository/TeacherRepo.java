package Repository;

import Model.Teacher;
import Model.Course;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepo extends InMemoryRepo<Teacher> implements FileRepo<Teacher> {

    String file;

    public TeacherRepo(String file) {
        super();
        this.file = file;
    }

    @Override
    public String toString() {
        return "TeacherRepo{" +
                "repoList=" + repoList +
                '}';
    }

    @Override
    public Teacher create(Teacher obj) throws IOException {
        for (Teacher teacher : repoList) {
            if (teacher.getTeacherID() == obj.getTeacherID())
                throw new IllegalArgumentException("Profesorul este deja in lista");
        }

        this.repoList.add(obj);
        writeToFile();
        return obj;
    }

    /**
     * schimb insusirile unui profesor
     *
     * @param obj cu care actualizez profesorul
     * @return profesorul actualizat
     */
    @Override
    public Teacher update(Teacher obj) throws IOException {

        Teacher TeacherUpdated = this.repoList.stream()
                .filter(teacher -> teacher.getTeacherID() == obj.getTeacherID())
                .findFirst()
                .orElseThrow();

        TeacherUpdated.setLastName(obj.getLastName());
        TeacherUpdated.setFirstName(obj.getFirstName());
        TeacherUpdated.setcourses(obj.getcourses());

        writeToFile();

        return TeacherUpdated;
    }

    @Override
    public boolean delete(Long teacherID) throws IllegalAccessException, IOException {
        if (repoList.isEmpty())
            throw new IndexOutOfBoundsException("Lista vida");

        for (Teacher teacher : repoList) {
            if (teacher.getTeacherID() == teacherID) {
                repoList.remove(teacher);
                writeToFile();
                return true;
            }

        }

        return false;
    }

    /**
     * citeste profesorii din teacher.json File
     *
     * @return lista acestora
     * @throws IOException
     */
    @Override
    public List<Teacher> readFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader("teacher.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            int id = pm.path("teacherID").asInt();

            String firstName = pm.path("firstName").asText();

            String lastName = pm.path("lastName").asText();

            List<Long> courses = new ArrayList<>();
            for (JsonNode v : pm.path("courses")) {
                courses.add(v.asLong());
            }


            Teacher teacher = new Teacher(firstName, lastName, courses, id);
            repoList.add(teacher);
        }

        return repoList;
    }

    /**
     * scriu cursuri in teacher.json File
     *
     * @throws IOException
     */
    @Override
    public void writeToFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        writer.writeValue(new File("teacher.json"), repoList);

    }

    @Override
    public Teacher findOne(Long id) throws IOException {
        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            long idTeacher = pm.path("idTeacher").asLong();
            if (id == idTeacher) {
                String firstName = pm.path("firstName").asText();

                String lastName = pm.path("lasrName").asText();

                List<Long> courses = new ArrayList<>();
                for (JsonNode v : pm.path("courses")) {
                    courses.add(v.asLong());
                }


                return new Teacher(firstName, lastName, courses, idTeacher);


            }
        }

        return null;
    }

    public boolean deleteCourse(Teacher teacher,Course course) throws IOException {
        if(!teacher.getcourses().contains(course.getID()))
            return false;

        teacher.deleteCourse(course);
        writeToFile();
        return true;
    }

    /**
     *verific existenta unui profesor
     * @param id
     * @return true, daca exista
     */
    public boolean containsID(Long id)
    {
        for(Teacher teacher : repoList)
        {
            if(teacher.getTeacherID() == id)
                return true;
        }
        return false;
    }

    /**
     *  verific daca profesorul preda un curs
     * @param teacher
     * @param id
     * @return true, daca il preda
     */
    public boolean containsCourse(Teacher teacher, Long id)
    {
        return teacher.containsCourse(id);
    }


    public void deleteCourseFromAll(Course course) throws IOException {
        for(Teacher teacher : repoList)
        {
            teacher.deleteCourse(course);
        }
        writeToFile();

    }

    public void addCourse(long id, Course course) throws IOException {

        Teacher teacher = null;
        for(Teacher teacher1 : repoList)
        {
            if(id == teacher1.getTeacherID())
                teacher = teacher1;
        }
        assert teacher != null;
        teacher.addCourse(course);
        writeToFile();
    }
}
