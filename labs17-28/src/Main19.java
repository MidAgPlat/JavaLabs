import java.io.IOException;

public class Main19 {
    public static void main(String[] args) throws IOException {
        int students = 20;


        while(students!=0){
            Tickets a = new Tickets(students);
            a.start();
            int buf = students;
            while(buf==students){
               students = a.studentsLeft;
            }
        }

        System.out.println("all tickets had been given out");

    }
}
