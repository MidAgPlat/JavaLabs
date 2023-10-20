import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main18 {

    public static void main(String[] args) throws IOException {

        Semaphore sem = new Semaphore(1);
        Fib a = new Fib(sem, 0, 1);
        a.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        while(i<5){
            Fib b = new Fib(sem, a.previous, a.current);
            b.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            a.previous = b.previous;
            a.current = b.current;
            i++;
        }
    }


}

