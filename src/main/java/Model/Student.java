package Model;

import java.util.List;
import java.util.ArrayList;

public class Student extends Person implements Comparable<Student>{

    long studentID;
    int totalCredits;
    List<Long> enrolledCourses;

    public Student(String firstName, String lastName, long studentID, int totalCredits, List<Long> enrolledCourses) {
        super(firstName, lastName);
        this.studentID=studentID;
        this.totalCredits=totalCredits;
        this.enrolledCourses=enrolledCourses;
    }

    public Student(String firstName, String lastName, long studentID) {
        super(firstName, lastName);
        this.studentID = studentID;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", studentID=" + studentID +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" +  enrolledCourses +
                '}';
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Student student = (Student) obj;
        return studentID == student.studentID && totalCredits == student.totalCredits && enrolledCourses.equals(student.enrolledCourses);
    }

    /**
     * sterg un curs din lista de studenti
     * @param course care va fi sters
     */
    public void deleteCourse(Course course)
    {
        this.enrolledCourses.remove(course.getID());
        this.totalCredits -= course.getCredits();
    }

    /**
     * verficam cat ii mai trebuie unui student, ca sa ajunga la 30 de credite
     * @return numarul de credite de care mai are nevoie
     */


    /**
     * actaulizez lista de cursuri a unui student + numarul de credite
     * @param course unde s-a inscris studentul
     */
    public void enrolled(Course course)
    {
        this.enrolledCourses.add(course.getID());
        this.totalCredits += course.getCredits();
    }

    /**
     * numarul de cursuri la care s-a inscris studentul
     * @return numarul respectiv
     */
    public int getNumberCourses()
    {
        return this.enrolledCourses.size();
    }

    /**
     * compar numarul de cursuri intre 2 studenti
     * @param student
     * @return studentul numarul mai mic de cursuri
     */
    @Override
    public int compareTo(Student student) {
        return Integer.compare(this.getNumberCourses(), student.getNumberCourses());

    }

    /**
     * calculez cate credite am nevoie pana la 3o
     * @return numarul de credite
     */
    public int getEnoughCredits()
    {
        return (30 - this.getTotalCredits());
    }


}
