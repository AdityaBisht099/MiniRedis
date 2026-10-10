import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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
}