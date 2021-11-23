package Controller;

import Model.Course;
import Model.Teacher;
import Repository.CourseRepo;
import Repository.TeacherRepo;
import Repository.StudentRepo;

import java.io.IOException;
import java.util.List;

public class TeacherContr implements Contr<Teacher>{

    private TeacherRepo teacherRepo;
    private StudentRepo studentRepo;
    private CourseRepo courseRepo;

    public TeacherContr(TeacherRepo teacherRepo, StudentRepo studentRepo, CourseRepo courseRepo) {
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public TeacherRepo getTeacherRepo() {
        return teacherRepo;
    }

    public void setTeacherRepo(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public StudentRepo getStudentRepo() {
        return studentRepo;
    }

    public void setStudentRepo(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public CourseRepo getCourseRepo() {
        return courseRepo;
    }

    public void setCourseRepo(CourseRepo courseRepo) {
        this.courseRepo= courseRepo;
    }



    @Override
    public Teacher create(Teacher obj) throws IOException {
        return teacherRepo.create(obj);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepo.getAll();
    }



    @Override
    public Teacher update(Teacher obj) throws IOException {
        return teacherRepo.update(obj);
    }


    @Override
    public boolean delete(Long objID) throws IOException, IllegalAccessException {
        return teacherRepo.delete(objID);
    }


    @Override
    public List<Teacher> readFromFile() throws IOException {
        return teacherRepo.readFromFile();
    }


    @Override
    public void writeToFile() throws IOException {
       teacherRepo.writeToFile();
    }


    @Override
    public Teacher findOne(Long id) throws IOException {
        return teacherRepo.findOne(id);
    }



    public boolean deleteCourse(Teacher teacher,Course course) throws IOException
    {
        if(teacherRepo.deleteCourse(teacher, course))
        {
            studentRepo.deleteCourse(course);
            courseRepo.delete(course.getID());
            return true;
        }
        return false;
    }


    public boolean containsID(long id)
    {
        return teacherRepo.containsID(id);
    }


    public boolean containsKurs(Teacher teacher, Long id)
    {
        return teacherRepo.containsCourse(teacher, id);
    }


    public void deleteCourseFromAll(Course course) throws IOException {
        teacherRepo.deleteCourseFromAll(course);
    }


    public void addCourse(long teacher, Course course) throws IOException {
       teacherRepo.addCourse(teacher, course);
    }


}
