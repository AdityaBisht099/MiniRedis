public class PersistenceTest {
    public static void main(String[] args) throws Exception {
        PersistenceManager persistence = new PersistenceManager();
        persistence.append("SET name Aditya");
        persistence.append("SET age 20");
        persistence.append("DELETE age");
    }
}