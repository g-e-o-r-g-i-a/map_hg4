package Model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person{

    private List<Long> courses;
    private long teacherID;

    public Teacher(String fistName, String lastName, List<Long> courses, long teacherID) {
        super(fistName, lastName);
        this.courses = courses;
        this.teacherID = teacherID;
        this.courses=courses;
    }

    public Teacher(String fistName, String lastName, long teacherID) {
        super(fistName, lastName);
        this.courses = new ArrayList<>();
        this.teacherID = teacherID;
    }

    public List<Long> getcourses() {
        return courses;
    }

    public void setcourses(List<Long> courses) {
        this.courses = courses;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "courses=" + courses +
                ", teacherID=" + teacherID +
                '}';
    }

    /**
     * mi se adauga un curs in lista de cursuri a profesului
     * @param course
     */
    public void addCourse(Course course)
    {
        this.courses.add(course.getID());
    }

    /**
     * mi se sterge un curs in lista de cursuri a profesului
     * @param course
     */
    public void deleteCourse(Course course)
    {
        this.courses.remove(course.getID());
    }

    public boolean containsCourse(Long id)
    {
        return this.courses.contains(id);
    }

}


