public class RespValue {
    enum Type {
        SIMPLE_STRING,
        BULK_STRING,
        NULL,
        ERROR
    }
    private final Type type;
    private final String value;
    public RespValue(Type type, String value) {
        this.type = type;
        this.value = value;
    }
    public Type getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
    @Override
    public String toString() {
        return type + ": " + value;
    }
}