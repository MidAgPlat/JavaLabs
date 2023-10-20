import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CopyUtil22 {
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

        Thread writer1 = new Thread( () -> {
            try {
                //lock.lock();
                for(int i = 0; i<64 ; i++){
                    output[i]= buffer.get(i);
                }
                System.out.println();
            } catch (Exception e) {System.out.println("buffer is empty");}
            finally {
                //lock.unlock();
            }
        });

        writer1.start();
        System.out.println(writer1.getName());

        Thread writer2 = new Thread( () -> {
            try {
                //lock.lock();
                for(int i = 0; i<64 ; i++){
                    output[i]= buffer.get(i);
                }
            } catch (Exception e) {System.out.println("buffer is empty");}
            finally {
                //lock.unlock();
            }
        });

        writer2.start();
        System.out.println(writer2.getName());

        Thread writer3 = new Thread( () -> {
            try {
                //lock.lock();
                for(int i = 0; i<64 ; i++){
                    output[i]= buffer.get(i);
                }
            } catch (Exception e) {System.out.println("buffer is empty");}
            finally {
                //lock.unlock();
            }
        });

        writer3.start();
        System.out.println(writer3.getName());

        while(lock.isLocked()){
            System.out.println("wait");
        }
    }
}
