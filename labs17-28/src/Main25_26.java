import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class Main25_26 {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentStack<Integer> t = new ConcurrentStack<>();
        PushThread<Integer> pusht;
        PopThread<Integer> popt1 = new PopThread<>(t);
        PopThread<Integer> popt2 = new PopThread<>(t);
        ArrayList<Integer> al = new ArrayList<>();
        for(int i = 0; i <= 42; i++) {
            al.add(i);
        }
        pusht = new PushThread<>(t,al);
        pusht.start();
        pusht.join();
        popt1.start();
        popt2.start();
        popt1.join();
        popt2.join();
        System.out.println(popt1.al);
        System.out.println(popt2.al);
    }
}

class ConcurrentStack <E> {
    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();
    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }
    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }
    public boolean hasNext() {
        return top.get() == null ? false : true;
    }
    private static class Node <E> {
        public final E item;
        public Node<E> next;
        public Node(E item) {
            this.item = item;
        }
    }
}
class PopThread<T> extends Thread{
    ConcurrentStack<T> t;
    ArrayList<T> al = new ArrayList<T>();
    T buf;
    PopThread(ConcurrentStack<T> t){
        this.t = t;
    }
    public void run() {
        while(t.hasNext()) {
            buf = t.pop();
            if (buf != null) al.add(buf);
        }
    }
}
class PushThread<T> extends Thread{
    ConcurrentStack<T> t;
    Collection<T> c;
    PushThread(ConcurrentStack<T> t, Collection<T> c){
        this.t = t;
        this.c = c;
    }
    public void run(){
        for(T element: c) {
            t.push(element);
        }
    }
}
