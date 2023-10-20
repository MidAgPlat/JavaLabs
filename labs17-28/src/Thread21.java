public class Thread21 extends Thread{
    int num;
    public Thread21 (int i){
        this.num = i;
    }

    @Override
    public void run(){
        for(int j = 0; j<6;j++){
            System.out.println(j);

            try{
                Thread.sleep(1000);

            }
            catch (InterruptedException e){
                System.out.println("Thread Interrupted");
            }
        }
    }
}
