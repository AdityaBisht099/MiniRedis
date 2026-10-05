import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
public class ThreadPoolExperiment {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(()->{
            System.out.println("Task 1 - " + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 1 finished");
        });
        executor.submit(()->{
            System.out.println("Task 2 - " + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 2 finished");
        });
        executor.submit(()->{
            System.out.println("Task 3 - " + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 3 finished");
        });
        executor.submit(() -> {
            System.out.println("Task 4 - " + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 4 finished");
        });
        executor.submit(()->{
            System.out.println("Task 5 - " + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 5 finished");
        });
        executor.shutdown();
    }
}