public class CommandHandler {
    public RespValue execute(String[] parts, KeyValueStore store) {
        if(parts.length == 0){
            return new RespValue(RespValue.Type.ERROR, "empty command");
        }
        if(parts.length == 3 && parts[0].equals("SET")){
            store.set(parts[1], parts[2]);
            return new RespValue(RespValue.Type.SIMPLE_STRING, "OK");
        }
        else if(parts.length == 2 && parts[0].equals("GET")){
            String value = store.get(parts[1]);
            if (value == null) {
                return new RespValue(RespValue.Type.NULL, null);
            }
            return new RespValue(RespValue.Type.BULK_STRING, value);
        }
        else if(parts.length == 2 && parts[0].equals("DELETE")){
            store.delete(parts[1]);
            return new RespValue(RespValue.Type.SIMPLE_STRING, "Key DELETED");
        }
        else if(parts.length == 1 && parts[0].equals("EXIT")){
            return new RespValue(RespValue.Type.SIMPLE_STRING, "EXIT");
        }
        else{
            return new RespValue(RespValue.Type.ERROR, "unknown command");
        }
    }
}