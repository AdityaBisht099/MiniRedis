public class EntryTest {
    public static void main(String[] args) {
        Entry permanentEntry = new Entry("Aditya", -1);
        Entry expiredEntry = new Entry("Potato", 1000);

        System.out.println(permanentEntry.getValue());
        System.out.println(permanentEntry.isExpired());

        System.out.println(expiredEntry.getValue());
        System.out.println(expiredEntry.isExpired());
    }
}