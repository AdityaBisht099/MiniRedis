public class RaceConditionExperiment{
    static int count=0;
    static synchronized void increment(){
        count++;
    }
    public static void main(String[] args){
        Thread threadA = new Thread(()->{
            for(int i=0;i<100000;i++){
                increment();
            }
        });
        threadA.start();
        Thread threadB = new Thread(()->{
            for(int i=0;i<100000;i++){
                increment();
            }
        });
        threadB.start();
        try{
            threadA.join();
            threadB.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(count);
    }
}
