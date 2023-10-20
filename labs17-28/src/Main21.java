import java.io.IOException;

public class Main21 {

    public static void main(String[] args) throws IOException {
        for(int i = 0; i <6; i++){
            Thread21 asyncThread = new Thread21(i);
            asyncThread.run();
        }

    }

}
