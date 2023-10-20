import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main20 {
    private static final class Producer implements Runnable {
        private final ReentrantLock lock;
        private final Queue < Integer > queue;
        private final Random random = new Random();

        public Producer(ReentrantLock lock, Queue < Integer > queue) {
            this.lock = lock;
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("Running producer in thread " + Thread.currentThread());
            while (true) {
                lock.lock();
                try {
                    System.out.println("Producer has the lock");
                    // Produce data here
                    for (int i = 0; i < 5; i++) {
                        int randomNumber = random.nextInt(100);
                        System.out.println("Producing " + randomNumber);
                        queue.add(randomNumber);
                    }

                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.out.println(ex);
                } finally {
                    System.out.println("Producer letting go of the lock");
                    lock.unlock();
                }

                try {
                    // Sleep here so this thread doesn't re-acquire the lock immediately
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.out.println(ex);
                }
            }
        }
    }


    private static final class Consumer implements Runnable {
        private final ReentrantLock lock;
        private final Queue < Integer > queue;

        public Consumer(ReentrantLock lock, Queue < Integer > queue) {
            this.lock = lock;
            this.queue = queue;
        }

        @Override
        public void run() {

            System.out.println("Running consumer in thread " + Thread.currentThread());

            while (true) {
                lock.lock();
                try {
                    System.out.println("Consumer has the lock");

                    while (!queue.isEmpty()) {
                        System.out.println("Consuming " + queue.poll());
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.out.println(ex);
                } finally {
                    System.out.println("Consumer letting go of the lock");
                    lock.unlock();
                }

                try {
                    // Sleep here so this thread doesn't re-acquire the lock immediately
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.out.println(ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        int capacity = 10;
        ReentrantLock lock = new ReentrantLock();
        Queue< Integer > queue = new ArrayDeque();

        Producer producer = new Producer(lock, queue);
        Consumer consumer = new Consumer(lock, queue);

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(consumer);
        service.submit(producer);

        service.shutdown();

    }

}
