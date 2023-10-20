import java.util.concurrent.Semaphore;

public class Fib extends Thread {

    int sum;
    Semaphore sem;
    int previous;
    int current;

    public Fib(Semaphore sem, int previous, int current){
        this.sem = sem;
        this.previous = previous;
        this.current = current;
    }

    public void Test()
    {
        sum = previous + current;
        previous = current;
        current = sum;
        System.out.print(" " + sum);
    }

    public void run()
    {
        try
        {
            System.out.println("wait");
            sem.acquire(1);
            sum = previous + current;
            previous = current;
            current = sum;
            System.out.print(" " + sum);

            sem.release(1);
        }
        catch(InterruptedException e)
        {
            System.out.println ("it wont");
        }
        finally {

            System.out.println(" fib num");
        }
    }

}
