import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main22 {

    public static void main(String[] args) throws IOException {
        Random rnd = new Random(0);
        byte[] testData = new byte[64];
        rnd.nextBytes(testData);

        byte[] output = new byte[64];
        CopyUtil22.CopyPaste(testData, output);


        if (!Arrays.equals(testData, output)) {
            throw new AssertionError("Lab decision wrong!");
        } else {
            System.out.println("OK!");
        }
    }
}
