import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class KeyValueStore {
    private final Map<String, Entry> map = new ConcurrentHashMap<>();
    public void set(String key, String value) {
        map.put(key, new Entry(value, -1));
    }
    public void set(String key, String value, long ttlSeconds) {
        long expirationTime =
                System.currentTimeMillis() + ttlSeconds * 1000L;
        setWithExpiration(key, value, expirationTime);
    }
    public void setWithExpiration(
            String key,
            String value,
            long expirationTime) {
        map.put(key, new Entry(value, expirationTime));
    }
    public String get(String key) {
        Entry entry = map.get(key);
        if (entry == null) {
            return null;
        }
        if (entry.isExpired()) {
            map.remove(key, entry);
            return null;
        }
        return entry.getValue();
    }
    public void delete(String key) {
        map.remove(key);
    }
    public long getTTL(String key) {
        Entry entry = map.get(key);
        if (entry == null) {
            return -2;
        }
        if (entry.isExpired()) {
            map.remove(key, entry);
            return -2;
        }
        if (entry.getExpirationTime() == -1) {
            return -1;
        }
        long remainingTime = entry.getExpirationTime()
                - System.currentTimeMillis();
        return (remainingTime + 999) / 1000;
    }
}