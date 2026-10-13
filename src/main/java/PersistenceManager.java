import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
public class PersistenceManager {
    private final Path file = Path.of("mini-redis.aof");
    public void append(String command) throws IOException {
        Files.writeString(
                file,
                command + "\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
    public List<String> loadCommands() throws IOException {
        if (Files.notExists(file)) {
            return List.of();
        }
        return Files.readAllLines(file);
    }
    public void recover(
            KeyValueStore store,
            CommandParser parser,
            CommandHandler handler) throws IOException {
        List<String> commands = loadCommands();
        for (String command : commands) {
            String[] parts = parser.parse(command);
            try {
                handler.execute(parts, store, this, true);
            } catch (IOException e) {
                throw new IOException("Failed to recover command: " + command, e);
            }
        }
    }
}