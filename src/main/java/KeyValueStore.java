import java.util.HashMap;
import java.util.Map;
public class KeyValueStore {
    private final Map<String, String> map = new HashMap<>();
    public void set(String key, String value) {
        map.put(key, value);
    }
    public String get(String key) {
        return map.get(key);
    }
    public void delete(String key) {
        map.remove(key);
    }
}