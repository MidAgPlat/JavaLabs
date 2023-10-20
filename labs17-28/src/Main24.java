import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class Main24 {

    public static void main(String[] args) throws IOException, InterruptedException {
        SequenceGenerator sg = new SequenceGenerator();
        List<Sequence> sequences = new ArrayList<Sequence>();
        for (int i = 0; i < 10; i++) {
            Sequence seq = new Sequence(i + 1, 10, sg);
            sequences.add(seq);
        }

        int summa;
        do {
            summa = 0;
            for (int i = 0; i < sequences.size(); i++) {
                if (!sequences.get(i).thread.isAlive()) {
                    sequences.get(i).printSequence();
                    summa++;
                }
            }
                Thread.sleep(100);

        } while (summa < sequences.size()) ;
        System.exit(0);
    }
}

class SequenceGenerator
{
    private AtomicLong element;
    public SequenceGenerator()
    {
        element = new AtomicLong(1);
    }
    public long next()
    {
        long value;
        long next;
        do {
            value = element.get();
            next = value + 1;
        } while (!element.compareAndSet(value, next));
        return value;
    }
}
class Sequence extends Thread
{
    Thread thread;
    int id;
    int count;
    SequenceGenerator sg;
    List<Long> sequence;
    boolean printed = false;
    Sequence(final int id, final int count, SequenceGenerator sg)
    {
        sequence = new ArrayList<Long>();
        this.count = count;
        this.id = id;
        this.sg = sg;
        thread = new Thread(this);
        System.out.println(id + "was created");
        thread.start();
    }
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                sequence.add(sg.next());
                Thread.sleep((long) (
                        (Math.random()*2 + 1)*30));
            }
        } catch (InterruptedException e) {
            System.out.println(id + " was interrupted");
        }
        System.out.println(id + " was finished");
        printSequence();
    }
    public void printSequence()
    {
        if (printed)
            return;
        String tmp = "[";
        for (int i = 0; i < sequence.size(); i++) {
            if (i > 0)
                tmp += ",";
            String nb = String.valueOf(sequence.get(i));
            while (nb.length() < 9)
                nb = " " + nb;
            tmp += nb;
        }
        tmp += "]";
        System.out.println("Sequence of thread"
                + id + " : " + tmp);
        printed = true;
    }
}
