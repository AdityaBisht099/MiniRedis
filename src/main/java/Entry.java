public class Entry {
    private final String value;
    private final long expirationTime;
    public Entry(String value, long expirationTime) {
        this.value = value;
        this.expirationTime = expirationTime;
    }
    public String getValue() {
        return value;
    }
    public boolean isExpired() {
        return expirationTime != -1 && System.currentTimeMillis() >= expirationTime;
    }
    public long getExpirationTime() {
        return expirationTime;
    }
}