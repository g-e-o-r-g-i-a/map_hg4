package Model;

import java.util.ArrayList;
import java.util.List;

public class Course implements Comparable<Course>{
    private String name;
    private long ID;
    private long teacher;
    private int maxEnrollment;
    private List<Long> studentsEnrolled;
    private int credits;

    public Course(String name, long ID, long teacher, int maxEnrollment, List<Long> studentsEnrolled, int credits) {
        this.name = name;
        this.ID = ID;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }

    public Course(long ID, int credits) {
        this.ID = ID;
        this.name = "";
        this.teacher = 0L;
        this.maxEnrollment = 0;
        this.studentsEnrolled = new ArrayList<>();
        this.credits = credits;
    }

    public Course(String name, Long idCourse, int maxEnrollment, List<Long> studentenEnrolled, int credits) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID(){
        return ID;
    }

    public void setID(long ID) {
        this.ID=ID;
    }

    public long getTeacher() {
        return teacher;
    }

    public void setTeacher(long teacher) {
        this.teacher = teacher;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Course{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }

    /**
     * verificam locurile libere in cadrul unui curs
     * @return true, daca exista locuri si false pentru contrariul
     */
    public boolean free()
    {
        return this.getStudentsEnrolled().size() < this.getMaxEnrollment();
    }


    /**
     * adaug un student in lista de studenti
     */
    public void addStudent(Student student)
    {
        this.studentsEnrolled.add(student.getStudentID());
    }

    /**
     * verificam numarul de locuri libere
     * @return numarul respectiv
     */
    public int getFree()
    {
        return (this.getMaxEnrollment() - this.getStudentsEnrolled().size());
    }


    /**
     * in functie de numarul de locuri libere, compar 2 cursuri
     * @param course
     * @return 1, daca primul curs are mai multe locuri, -1, daca primul curs are mai putine locuri, 0, daca au acelasi numar de locuri
     */

    public int compareTo(Course course) {
        if(this.getFree() < course.getFree())
            return -1;

        if(this.getFree() < course.getFree())
            return 0;

        return 1;
    }
}


