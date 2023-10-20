
public class Tickets extends Thread {
    public int studentsLeft;

    public Tickets(int studentsLeft)
    {
        this.studentsLeft = studentsLeft;
    }


    public synchronized void run() {
        try
        {
            if(studentsLeft>=3){
                System.out.println("3 tickets had been given away");
                studentsLeft-=3;
                System.out.println("Waiting for a month");
                Thread.sleep(30000);
                System.out.println("Waiting has ended");
            }
            System.out.println(studentsLeft  + " tickets had been given away");
            studentsLeft = 0;
        }
        catch(InterruptedException e)
        {
            System.out.println ("it wont");
        }
    }
}
