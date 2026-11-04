import java.io.IOException;

public class CommandHandler {
    public RespValue execute(String[] parts, KeyValueStore store, PersistenceManager persistence,  boolean recovering) throws IOException{
        if(parts.length == 0){
            return new RespValue(RespValue.Type.ERROR, "empty command");
        }
        if (parts[0].equals("SET") && parts.length == 3) {
            store.set(parts[1], parts[2]);
            try {
                if (!recovering) {
                    persistence.append("SET " + parts[1] + " " + parts[2]);
                }
            } catch (IOException e) {
                return new RespValue(
                        RespValue.Type.ERROR,
                        "Could not save command"
                );
            }
            return new RespValue(RespValue.Type.SIMPLE_STRING, "OK");
        }
        else if (parts[0].equals("SET") && parts.length == 5
                && parts[3].equals("EX")) {
            try {
                long ttlSeconds = Long.parseLong(parts[4]);
                if (ttlSeconds <= 0) {
                    return new RespValue(
                            RespValue.Type.ERROR,
                            "TTL must be greater than zero"
                    );
                }
                long expirationTime =
                        System.currentTimeMillis() + ttlSeconds * 1000L;
                store.setWithExpiration(
                        parts[1],
                        parts[2],
                        expirationTime
                );
                if (!recovering) {
                    persistence.append(
                            "SETAT " + parts[1] + " "
                                    + parts[2] + " " + expirationTime
                    );
                }
                return new RespValue(RespValue.Type.SIMPLE_STRING, "OK");
            } catch (NumberFormatException e) {
                return new RespValue(
                        RespValue.Type.ERROR,
                        "Invalid TTL value"
                );
            } catch (IOException e) {
                return new RespValue(
                        RespValue.Type.ERROR,
                        "Could not save command"
                );
            }
        }
        else if (parts.length == 4 && parts[0].equals("SETAT")) {
            try {
                long expirationTime = Long.parseLong(parts[3]);
                if (expirationTime <= System.currentTimeMillis()) {
                    return new RespValue(
                            RespValue.Type.SIMPLE_STRING,
                            "OK"
                    );
                }
                store.setWithExpiration(
                        parts[1],
                        parts[2],
                        expirationTime
                );
                return new RespValue(
                        RespValue.Type.SIMPLE_STRING,
                        "OK"
                );

            } catch (NumberFormatException e) {
                return new RespValue(
                        RespValue.Type.ERROR,
                        "Invalid expiration timestamp"
                );
            }
        }
        else if(parts.length == 2 && parts[0].equals("GET")){
            String value = store.get(parts[1]);
            if (value == null) {
                return new RespValue(RespValue.Type.NULL, null);
            }
            return new RespValue(RespValue.Type.BULK_STRING, value);
        }
        else if (parts.length == 2 && parts[0].equals("DELETE")) {
            store.delete(parts[1]);
            try {
                if (!recovering) {
                    persistence.append("DELETE " + parts[1]);
                }
            } catch (IOException e) {
                System.out.println("Could not save command: " + e.getMessage());
                return new RespValue(
                        RespValue.Type.ERROR,
                        "Could not save command"
                );
            }
            return new RespValue(
                    RespValue.Type.SIMPLE_STRING,
                    "Key DELETED"
            );
        }
        else if(parts.length == 1 && parts[0].equals("EXIT")){
            return new RespValue(RespValue.Type.SIMPLE_STRING, "EXIT");
        }
        else if (parts.length == 2 && parts[0].equals("TTL")) {
            long ttl = store.getTTL(parts[1]);
            return new RespValue(
                    RespValue.Type.INTEGER,
                    String.valueOf(ttl)
            );
        }
        else {
            return new RespValue(RespValue.Type.ERROR, "unknown command");
        }
    }
}