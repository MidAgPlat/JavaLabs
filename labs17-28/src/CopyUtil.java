import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class CopyUtil{

    static void CopyPaste(byte[] input, byte[] output){

        ReentrantLock lock = new ReentrantLock();
        List<Byte> buffer = new ArrayList<Byte>();

        Thread reader = new Thread( () -> {
            try {
                lock.lock();
                for(int i = 0; i<input.length; i++){
                    buffer.add(input[i]);
                }
            } catch (Exception e) {System.out.println("input is empty");}
            finally {
                lock.unlock();
            }
        });
        reader.start();

        Thread writer = new Thread( () -> {
            try {
                lock.lock();
                for(int i = 0; i<64 ; i++){
                    output[i]= buffer.remove(0);
                }
            } catch (Exception e) {System.out.println("buffer is empty");}
            finally {
                lock.unlock();
            }
        });

        writer.start();
    }

}