import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        Random rnd = new Random(0);
        byte[] testData = new byte[64];
        rnd.nextBytes(testData);

        byte[] output = new byte[64];
        CopyUtil.CopyPaste(testData, output);


        if (!Arrays.equals(testData, output)) {
            throw new AssertionError("Lab decision wrong!");
        } else {
            System.out.println("OK!");
        }
    }
}
