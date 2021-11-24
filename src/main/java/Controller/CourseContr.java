package Controller;

import Exception.Exception1;
import Exception.Exception2;
import Model.Course;
import Model.Person;
import Model.Student;
import Model.Teacher;
import Repository.CourseRepo;
import Repository.StudentRepo;
import Repository.TeacherRepo;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class CourseContr implements Contr<Course> {
    private CourseRepo courseRepo;
    private StudentRepo studentRepo;
    private TeacherRepo teacherRepo;


    public CourseContr(CourseRepo courseRepo, StudentRepo studentRepo, TeacherRepo teacherRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo=studentRepo;
        this.teacherRepo = teacherRepo;
    }

    public CourseRepo getCourseRepo() {
        return courseRepo;
    }

    public void setCourseRepo(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public StudentRepo getStudentenRepo() {
        return studentRepo;
    }

    public void setStudentRepo(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public TeacherRepo getTeacherRepo() {
        return teacherRepo;
    }

    public void setTeacherRepo(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }


    @Override
    public Course create(Course obj) throws IOException,Exception1 {
        teacherRepo.addCourse(obj.getCredits(), obj);
        return courseRepo.create(obj);
    }

    @Override
    public List<Course> getAll() {
        return courseRepo.getAll();
    }

    @Override
    public Course update(Course obj) throws IOException, Exception2 {
        return courseRepo.update(obj);
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        return courseRepo.delete(objID);
    }

    @Override
    public List<Course> readFromFile() throws IOException {
        return courseRepo.readFromFile();
    }

    @Override
    public void writeToFile() throws IOException {
        courseRepo.writeToFile();

    }

    @Override
    public Course findOne(Long id) throws IOException {
        return courseRepo.findOne(id);
    }


    public List<Course> filter()
    {
        return courseRepo.filterList();
    }


    public void sort()
    {
        courseRepo.sortList();
    }


    public List<Course> getCourseFreeSpace ()
    {
        return courseRepo.getCourseFreeSpace();
    }



    public void coursesWithFreePlaces()
    {
        courseRepo.coursesWithFreePlaces();
    }




    public boolean register(Student student, Course course) throws IOException, Exception2 {
        if(courseRepo.hasFreePlace(course.getID()) && studentRepo.validationAddCourse(student, course))
        {
            if(!studentRepo.containsCourse(course.getID(), student))
            {
                courseRepo.addStudent(student, course.getID());
                studentRepo.addCourse(student, course);
                return true;
            }

        }
        return false;
    }

    public boolean containsID(long id)
    {
        return  courseRepo.containsID(id);
    }
}

