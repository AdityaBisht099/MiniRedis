import java.io.IOException;
import java.util.List;
public class PersistenceTest {
    public static void main(String[] args) throws IOException {
        PersistenceManager persistence = new PersistenceManager();
        List<String> commands = persistence.loadCommands();
        for (String command : commands) {
            System.out.println(command);
        }
    }
}