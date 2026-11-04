public class TTLTest {
    public static void main(String[] args) throws InterruptedException {
        KeyValueStore store = new KeyValueStore();

        store.set("name", "Aditya");
        store.set("session", "Aditya", 2);

        System.out.println(store.get("name"));
        System.out.println(store.get("session"));

        Thread.sleep(3000);

        System.out.println(store.get("session"));
    }
}