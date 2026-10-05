public class ThreadExperiment {
    public static void main(String[] args) {
        Thread threadA =new Thread(() -> {
            System.out.println("Thread A here");
        });
        threadA.start();
        Thread threadB =new Thread(() -> {
            System.out.println("Thread B here");
        });
        threadB.start();
    }
}