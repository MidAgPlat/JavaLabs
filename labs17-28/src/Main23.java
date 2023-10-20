import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main23 {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i<10; i++) {
            String input = scanner.nextLine();
            addInput(input);
            System.out.println("the last said: " + getLastInput());
        }

        outWholeStack();
    }

    private static ThreadLocal<Stack<String>> threadLocalStack = new ThreadLocal<>() {

        @Override
        protected Stack<String> initialValue() {
            return new Stack<>();
        }
    };
    public static synchronized void addInput(String input) {
        Stack<String> stack = threadLocalStack.get();
        stack.push(input);
    }
    public static synchronized String getLastInput() {
        Stack<String> stack = threadLocalStack.get();
        if (stack.empty()) {
            return null;
        }
        return stack.peek();
    }

    public static synchronized void outWholeStack(){
        Stack<String> stack = threadLocalStack.get();
        while(!stack.empty()){
            System.out.println(stack.pop());
        }
    }
}
