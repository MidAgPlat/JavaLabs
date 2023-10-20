import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main29 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Stack size: ");
        int N = scanner.nextInt();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            stack.push(i);
        }

        int summ = 0;
        for (int i = 0; i < N; i++) {
            summ+=stack.get(i);
        }
        System.out.println("Stack summ: " + summ);
        stack.push(summ);


        System.out.print("Enter the number of queue items: ");
        N = scanner.nextInt();
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            queue.offer(scanner.nextInt());
        }
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int element = queue.poll();
            element *= -1;
            queue.offer(element);
        }
        System.out.println("After deleting odd: " + queue);
    }
}

