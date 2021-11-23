package Repository;

import Model.Course;
import Model.Student;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;

import Exception.Exception2;
import Exception.Exception1;
import java.util.*;

public class CourseRepo extends InMemoryRepo<Course> implements FileRepo<Course> {

    String file;

    public CourseRepo(String file) {
        super();
        this.file = file;
    }

    @Override
    public String toString() {
        return "CourseRepo{" +
                "repoList=" + repoList +
                '}';
    }

    /**
     * citesc din course.json File
     *
     * @return lista cursurilor
     * @throws IOException
     */
    @Override
    public List<Course> readFromFile() throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            long ID = pm.path("id").asLong();

            String name = pm.path("name").asText();

            int teacher = pm.path("teacher").asInt();

            int maxEnrollment = pm.path("maxEnrollment").asInt();

            List<Long> studentsEnrolled = new ArrayList<>();
            for (JsonNode v : pm.path("studentsEnrolled")) {
                studentsEnrolled.add(v.asLong());
            }

            int credits = pm.path("credits").asInt();

            Course course = new Course(name, ID, teacher, maxEnrollment, studentsEnrolled, credits);
            repoList.add(course);
        }

        return repoList;

    }

    /**
     * scriu curs nou in course.json
     *
     * @throws IOException
     */
    @Override
    public void writeToFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        writer.writeValue(new File(file), repoList);

    }


    @Override
    public Course create(Course obj) throws IOException, Exception1 {
        for (Course course : repoList) {
            if (course.getID() == obj.getID())
                throw new Exception1("Cursul e deja in lista");
        }

        this.repoList.add(obj);
        writeToFile();
        return obj;
    }

    /**
     * schimb insusirile unui curs
     *
     * @param obj de care ma folosesc sa actualizez datele
     * @return cursul actualizat
     * @throws IOException
     * @throws Exception2, daca lista e goala
     */
    @Override
    public Course update(Course obj) throws IOException {


        Course CourseUpdated = this.repoList.stream()
                .filter(course -> Objects.equals(course.getID(), obj.getID()))
                .findFirst()
                .orElseThrow();

        CourseUpdated.setName(obj.getName());
        CourseUpdated.setTeacher(obj.getTeacher());
        CourseUpdated.setMaxEnrollment(obj.getMaxEnrollment());
        CourseUpdated.setCredits(obj.getCredits());
        CourseUpdated.setStudentsEnrolled(obj.getStudentsEnrolled());

        writeToFile();
        return CourseUpdated;
    }

    /**
     * soretez in functie de numarul de locuri neocupate
     */
    public void sortList() {
        repoList.sort(Course::compareTo);
    }

    /**
     * caut toate cursurile cu mai mult de 15 credite
     *
     * @return lista cu respectivele
     */
    public List<Course> filterList() {
        return repoList.stream()
                .filter(course -> course.getCredits() > 15).toList();
    }

    /**
     * adaug un curs in lista
     *
     * @param obj, ce vrem sa adaugam
     * @return ce am adaugat
     * @throws IOException
     * @throws Exception1  daca cursul e deja in lista
     */

    public Course addCourse(Course obj) throws IOException, Exception1 {
        for (Course course : repoList) {
            if (course.getID() == obj.getID())
                throw new Exception1("Curs deja adaugat");
        }

        this.repoList.add(obj);
        writeToFile();
        return obj;
    }

    /**
     * caut cursurile cu locuri libere
     *
     * @return
     */
    public Map<Long, Integer> coursesWithFreePlaces() {
        Map<Long, Integer> freeCourses = new HashMap<>();
        for (Course course : repoList) {
            if (course.free()) {
                freeCourses.put(course.getID(), course.getFree());
            }
        }
        return freeCourses;

    }

    /**
     * verific daca am locuri neocupate la un curs
     *
     * @param idCourse
     * @return true daca am locuri, false pentru contrariul
     */
    public boolean hasFreePlace(Long idCourse) {
        boolean aux = false;
        for (Course course : repoList) {
            if (course.getID() == idCourse)
                if (course.free())
                    aux = true;

        }

        return aux;
    }


    public Course findOne(Long idCourse) throws IOException {
        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            Long ID = pm.path("id").asLong();
            if (Objects.equals(idCourse, ID)) {
                String name = pm.path("name").asText();

                int teacher = pm.path("teacher").asInt();

                int maxEnrollment = pm.path(" maxEnrollment").asInt();

                List<Long> studentenEnrolled = new ArrayList<>();
                for (JsonNode v : pm.path("studentenEnrolled")) {
                    studentenEnrolled.add(v.asLong());
                }

                int credits = pm.path("credits").asInt();

                return new Course(name, idCourse, maxEnrollment, studentenEnrolled, credits);
            }
        }

        return null;
    }


    public boolean delete(Long idCourse) throws IOException {
        if (repoList.isEmpty())
            throw new IndexOutOfBoundsException("Lista vida");

        for (Course course : repoList) {
            if (course.getID() == idCourse) {
                repoList.remove(course);
                writeToFile();
                return true;
            }

        }

        return false;
    }


    /**
     * verific daca un student participa la curs
     *
     * @param idCourse
     * @param idStudent
     * @return true, daca studentul se afla in lista
     */
    public boolean containsCourseStud(Long idCourse, Long idStudent) {
        boolean aux = false;
        for (Course course : repoList) {
            if (course.getID() == idCourse)
                if (!course.getStudentsEnrolled().contains(idStudent))
                    aux = true;

        }

        return aux;
    }

    /**
     * verific existenta unui curs
     *
     * @param id
     * @return true, daca exista, fals pentru contrariul
     */
    public boolean containsID(Long id) {
        for (Course course : repoList) {
            if (course.getID() == id)
                return true;
        }
        return false;
    }

    /**
     * adaug un nou student in lista
     *
     * @param student, pe care il adaug
     * @param idCourse
     * @throws IOException
     * @throws Exception2, daca lista e goala
     */
    public void addStudent(Student student, Long idCourse) throws IOException, Exception2 {
        if (hasFreePlace(idCourse) && containsCourseStud(idCourse, student.getStudentID())) {
            for (Course course : repoList) {
                if (idCourse == course.getID()) {
                    course.addStudent(student);
                    this.update(course);
                    writeToFile();
                }
            }

        }
    }

    /**
     *verific care cursuri mai sunt libere
     *
     * @return o lista cu acestea
     */
    public List<Course> getCourseFreeSpace() {
        List<Course> freeCourse = new ArrayList<>();
        for (Course course : repoList) {
            if (course.free()) {
                freeCourse.add(course);
            }
        }
        return freeCourse;
    }
}
