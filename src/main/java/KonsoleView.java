import Controller.CourseContr;
import Controller.TeacherContr;
import Controller.StudentContr;
import Model.Course;
import Model.Teacher;
import Model.Student;
import Exception.Exception2;
import java.io.IOException;
import java.util.Scanner;
import Exception.Exception1;
import java.util.List;

public class KonsoleView {

    private CourseContr courseContr;
    private TeacherContr teacherContr;
    private StudentContr studentContr;

    public KonsoleView(CourseContr courseContr, TeacherContr teacherContr, StudentContr studentContr) {
        this.courseContr = courseContr;
        this.teacherContr = teacherContr;
        this.studentContr = studentContr;
    }


    public CourseContr getCourseContr() {
        return courseContr;
    }

    public void setCourseContr(CourseContr courseContr) {
        this.courseContr = courseContr;
    }

    public TeacherContr getTeacherContr() {
        return teacherContr;
    }

    public void setTeacherContr(TeacherContr teacherContr) {
        this.teacherContr = teacherContr;
    }

    public StudentContr getStudentContr() {
        return studentContr;
    }

    public void setStudentContr(StudentContr studentContr) {
        this.studentContr = studentContr;
    }


    public void getMenu() {
        System.out.println("""
                1.register\s
                2.Courses with free places\s
                3.Students enrolled to a course\s
                4.See all\s
                5.Add\s
                6.Sort or Filter\s
                7.Exit\s
                """);
    }


    public void start() throws IOException, Exception2, Exception1, InterruptedException {
        while (true) {
            getMenu();
            Scanner keyboard = new Scanner(System.in);
            int key;
            do {
                System.out.print("Pick an option: ");
                key = keyboard.nextInt();
            }
            while (key<1 && key >7);

            long id;
            long idCourse;
            long idStudent;
            long idTeacher;

            switch (key) {
                case 1 -> {
                    do {
                        System.out.print("Pick a course: ");
                        idCourse = keyboard.nextInt();
                    }
                    while (!courseContr.containsID(idCourse));
                    do {
                        System.out.print("Pick a student: ");
                        idStudent = keyboard.nextInt();
                    }
                    while (!studentContr.containsID(idStudent));

                    Course course1 = courseContr.findOne(idCourse);
                    Student student = studentContr.findOne(idStudent);
                    courseContr.register(student, course1);
                }
                case 2 -> {
                    System.out.println("Free Courses:\n" + courseContr.getCourseFreeSpace());
                    Thread.sleep(3000);
                }
                case 3 -> {
                    System.out.println("ID:");
                    id = keyboard.nextLong();
                    if (courseContr.containsID(id)) {
                        studentContr.studTospecifcCourse(id);
                    }
                    case 4 -> {
                        case 3 -> {
                            getMenuShow();
                            getFunctionGetAll();
                        }
                        case 5 -> {
                            getAddMenu();
                            getFunctionAdd();
                        }
                        case 6 -> {
                            getMenuSortFilter();
                            getFunctionSortFilter();
                        }
                        case 7 -> {
                            System.out.println("EXIT");
                            System.exit(0);
                        }

                    }
                }

            }
        }
    }


    public Student createStudent()
    {
        Scanner scan= new Scanner(System.in);
        System.out.println("firstName:");
        String firstName= scan.nextLine();
        System.out.println("lastName:");
        String lastName= scan.nextLine();
        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while(studentContr.containsID(id));

        return new Student(firstName, lastName, id);

    }

    public Teacher createTeacher()
    {
        Scanner scan= new Scanner(System.in);
        System.out.println("firstName:");
        String firstName= scan.nextLine();
        System.out.println("lastName:");
        String lastName= scan.nextLine();
        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while(teacherContr.containsID(id));

        return new Teacher(firstName, lastName, id);

    }

    public Course createCourse()
    {
        Scanner scan= new Scanner(System.in);
        System.out.println("Name:");
        String name= scan.nextLine();

        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while( courseContr.containsID(id));

        long idTeacher;
        do{
            System.out.println("Teacher:");
            idTeacher= scan.nextLong();
        }while(!teacherContr.containsID(idTeacher));

        int maxEnrollment;
        do{
            System.out.println("maxEnrollment:");
            maxEnrollment= scan.nextInt();
        }while(maxEnrollment <= 0);

        int credits;
        do{
            System.out.println("ECTS:");
            credits= scan.nextInt();
        }while(credits <= 0);

        return new Course(name,id,idTeacher,maxEnrollment,credits);

    }


    public void getMenuSortFilter()
    {
        System.out.println("""
                1.Filter Courses\s
                2.Sort Courses\s
                3.Filter Students\s
                4.Sort Students\s
                """);
    }


    public void getFunctionSortFilter() throws InterruptedException {
        Scanner scan= new Scanner(System.in);
        int key;
        do {
            System.out.print("Pick an option ");
            key = scan.nextInt();
        }
        while(key<1 && key >4);

        switch (key) {
            case 1 -> {
                System.out.println(courseContr.filter());
                Thread.sleep(3000);
            }
            case 2 -> {
                courseContr.sort();
                System.out.println(courseContr.getAll());
                Thread.sleep(3000);
            }
            case 3 -> {
                System.out.println(studentContr.filter());
                Thread.sleep(3000);
            }
            case 4 -> {
                studentContr.sort();
                System.out.println(studentContr.getAll());
                Thread.sleep(3000);
            }
        }
    }


    public void getAddMenu()
    {
        System.out.println("""
                1.Add Courses\s
                2.Add Teachers\s
                3.Add Students\s
                """);
    }


    public void getFunctionAdd() throws IOException, Exception1 {
        Scanner scan= new Scanner(System.in);
        int key;
        do {
            System.out.print("Pick an option: ");
            key = scan.nextInt();
        }
        while(key<1 && key >3);

        switch (key) {
            case 1 -> {
                Course course = this.createCourse();
                courseContr.create(course);
            }
            case 2 -> {
                Teacher teacher = this.createTeacher();
                teacherContr.create(teacher);
            }
            case 3 -> {
                Student student = this.createStudent();
                studentContr.create(student);
            }
        }
    }


    public void getMenuShow()
    {
        System.out.println("""
                1.Show Courses\s
                2.Show Teachers\s
                3.Show Students\s
                """);
    }

    public void getFunctionGetAll() throws InterruptedException {
        Scanner scan= new Scanner(System.in);
        int key;
        do {
            System.out.print("Pick an option: ");
            key = scan.nextInt();
        }
        while(key<1 && key >3);

        switch (key) {
            case 1 -> {
                System.out.println("Courses:\n" + courseContr.getAll());
                Thread.sleep(3000);
            }
            case 2 -> {
                System.out.println("Teachers:\n" + teacherContr.getAll());
                Thread.sleep(3000);
            }
            case 3 -> {
                System.out.println("Students:\n" + studentContr.getAll());
                Thread.sleep(3000);
            }
        }
    }
}
}

