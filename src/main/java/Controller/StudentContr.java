package Controller;

import Model.Student;
import Repository.StudentRepo;

import java.io.IOException;
import java.util.List;

public class StudentContr implements Contr<Student>{

    private StudentRepo studentRepo;

    public StudentContr(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public StudentRepo getStudentRepo() {
        return studentRepo;
    }

    public void setStudentRepo(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }


    @Override
    public Student create(Student obj) throws IOException {
        return studentRepo.create(obj);
    }


    @Override
    public List<Student> getAll() {
        return studentRepo.getAll();
    }



    @Override
    public Student update(Student obj) throws IOException {
        return studentRepo.update(obj);
    }


    @Override
    public boolean delete(Long objID) {
        return delete(objID);
    }


    public List<Student> readFromFile() throws IOException {
        return studentRepo.readFromFile();
    }


    @Override
    public void writeToFile() throws IOException {
        studentRepo.writeToFile();
    }


    @Override
    public Student findOne(Long id) throws IOException {
        return studentRepo.findOne(id);
    }



    public List<Student> filter()
    {
        return studentRepo.filterList();
    }


    public void sort()
    {
        studentRepo.sortList();
    }


    public void studTospecifcCourse(Long courseId)
    {
        System.out.println(studentRepo.studTospecifcCourse(courseId));
    }


    public boolean containsID(long id)
    {
        return studentRepo.containsID(id);
    }


    public boolean containsCourse(Student student, long idCourse)
    {
        return studentRepo.containsCourse(idCourse, student);
    }
}