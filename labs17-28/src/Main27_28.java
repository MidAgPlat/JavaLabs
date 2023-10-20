import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Main27_28 {
    public static void main(String args[]) throws InterruptedException {
        LinkedQueue<Integer> lq = new LinkedQueue<>();
        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
        ArrayList<Integer> al1 = new ArrayList<>();
        ArrayList<Integer> al2 = new ArrayList<>();
        for(int i = 0; i <= 42; i++) {
            bq.add(i);
        }
        PutThread<Integer> putt1 = new PutThread<>(lq, bq);
        PollThread<Integer> pollt1 = new PollThread<>(lq, al1);
        PollThread<Integer> pollt2 = new PollThread<>(lq, al2);
        putt1.start();
        putt1.join();
        pollt1.start();
        pollt2.start();
        pollt1.join();
        pollt2.join();
        System.out.println(al1);
        System.out.println(al2);
    }
}

class LinkedQueue <E> {
    private static class Node <E> {
        final E item;
        final AtomicReference<Node<E>> next;
        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }
    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(dummy);
    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    if (curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }
    public E poll() {
        while (true) {
            if(head.get() != null) {
                Node<E> curHead = head.get();
                Node<E> headNext = curHead.next.get();
                if (curHead == head.get()) {
                    if (head.compareAndSet(curHead, headNext)) {
                        return curHead.item;
                    }
                }
            }else {
                return null;
            }
        }
    }
    public Node<E> peek() {
        return head.get();
    }
}
class PutThread<T> extends Thread{
    LinkedQueue<T> lq;
    BlockingQueue<T> sq;
    PutThread(LinkedQueue<T> lq, BlockingQueue<T> bq){
        this.lq = lq;
        this.sq = bq;
    }
    public void run() {
        while(sq.peek() != null) {
            lq.put(sq.poll());
        }
    }
}
class PollThread<T> extends Thread{
    T buf;
    LinkedQueue<T> lq;
    ArrayList<T> al;
    PollThread(LinkedQueue<T> lq, ArrayList<T> al){
        this.lq = lq;
        this.al = al;
    }
    public void run() {
        while(lq.peek() != null) {
            buf = lq.poll();
            if(buf != null) al.add(buf);
        }
    }
}
