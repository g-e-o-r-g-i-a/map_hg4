package Repository;

import Model.Course;
import Model.Student;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentRepo extends InMemoryRepo<Student> implements FileRepo<Student> {
    String file;

    public StudentRepo(String file) {
        super();
        this.file = file;
    }

    @Override
    public String toString() {
        return "StudentRepo{" +
                "repoList=" + repoList +
                '}';
    }

    @Override
    public List<Student> readFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            long ID = pm.path("studentID").asLong();

            String firstName = pm.path("firstName").asText();

            String lastName = pm.path("lastName").asText();

            int totalCredits = pm.path("totalCredits").asInt();

            List<Long> enrolledCourses = new ArrayList<>();
            for (JsonNode v : pm.path("enrolledCourses")) {
                enrolledCourses.add(v.asLong());
            }


            Student student = new Student(firstName,lastName,ID,totalCredits,enrolledCourses);
            repoList.add(student);
        }

        return repoList;
    }

    @Override
    public void writeToFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        writer.writeValue(new File(file), repoList);

    }

    @Override
    public Student findOne(Long id) throws IOException {
        Reader reader = new BufferedReader(new FileReader(this.file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser)
        {

            long ID = pm.path("studentID").asLong();
            if(Objects.equals(id, ID))
            {

                String firstName = pm.path("firstName").asText();

                String lastName = pm.path("lastName").asText();

                int allCredits = pm.path("allCredits").asInt();

                List<Long> enrolledCourses = new ArrayList<>();
                for (JsonNode v : pm.path("enrolledCourses"))
                {
                    enrolledCourses.add(v.asLong());
                }

                return new Student(firstName,lastName,ID,allCredits,enrolledCourses);

            }
        }

        return null;
    }


    @Override
    public Student update(Student obj) throws IOException {
        if (repoList.isEmpty())
            throw new IndexOutOfBoundsException("Lista vida");

        Student StudentUpdated = this.repoList.stream()
                .filter(student -> student.getStudentID() == obj.getStudentID())
                .findFirst()
                .orElseThrow();

        StudentUpdated.setLastName(obj.getLastName());
        StudentUpdated.setFirstName(obj.getFirstName());
        StudentUpdated.setTotalCredits(obj.getTotalCredits());
        StudentUpdated.setEnrolledCourses(obj.getEnrolledCourses());

        writeToFile();
        return StudentUpdated;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        if(repoList.isEmpty())
        throw new IndexOutOfBoundsException("Lista vida");

        for(Student student : repoList)
        {
            if(student.getStudentID() == objID)
            {
                repoList.remove(student);
                writeToFile();
                return true;
            }

        }
        return false;
    }

    public boolean validationAddCourse(Student student, Course course) {
        if (!repoList.contains(student))
            return false;

        return student.getEnoughCredits() >= course.getCredits();
    }


    /**
     * sortez lista studentilor in functie de numarul de credite
     */
    public void sortList()
    {
        repoList.sort(Student::compareTo);
    }

    /**
     * caut studentii care au 30 de credite
     * @return lista cu respectivii
     */
    public List<Student> filterList()
    {
        return repoList.stream()
                .filter(student->student.getTotalCredits() == 30).toList();
    }

    public Student create(Student obj) throws IOException {
        for(Student student : repoList)
        {
            if(student.getStudentID() == obj.getStudentID())
                throw new IllegalArgumentException("Studentul este deja in lista");
        }

        this.repoList.add(obj);
        writeToFile();
        return obj;
    }

    public List<Student> studTospecifcCourse(Long kursID)
    {
        List<Student> studentsEnrolled = new ArrayList<>();
        for(Student student : repoList)
        {
            List<Long> kurseStudent = student.getEnrolledCourses();
            for(Long kurs : kurseStudent)
            {
                if(Objects.equals(kurs, kursID))
                {
                    studentsEnrolled.add(student);
                    break;
                }
            }
        }
        return studentsEnrolled;
    }


    public void deleteCourse(Course course) throws IOException {
        for(Student student : repoList)
        {
            if(student.getEnrolledCourses().contains(course.getID()))
            {
                student.deleteCourse(course);
                writeToFile();
            }
        }
    }





    /**
     * adaug un curs in lista unui student
     * @param student
     * @param course
     * @throws IOException
     */
    public void addCourse(Student student, Course course) throws IOException {
        if(validationAddCourse(student,course))
        {
            student.enrolled(course);
            this.update(student);
            writeToFile();
        }
    }

    /**
     * verific existenta unui student
     * @param id
     * @return true, daca exista
     */
    public boolean containsID(Long id)
    {
        for(Student student : repoList)
        {
            if(student.getStudentID() == id)
                return true;
        }
        return false;
    }

    public boolean containsCourse(long idCourse, Student student)
    {
        return student.getEnrolledCourses().contains(idCourse);

    }

}