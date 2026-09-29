public class CommandHandler {
    public String execute(String[] parts, KeyValueStore store) {
        if (parts.length == 0) {
            return "";
        }
        if(parts.length==3 && parts[0].equals("SET")){
            store.set(parts[1], parts[2]);
            return "OK";
        }
        else if (parts.length == 2 && parts[0].equals("GET")) {
            return store.get(parts[1]);
        }
        else if(parts.length==2 && parts[0].equals("DELETE")){
            store.delete(parts[1]);
            return "Key DELETED";
        }
        else if(parts.length==1 && parts[0].equals("EXIT")){
            return "EXIT";
        }
        else{
            return "UNKNOWN COMMAND";
        }
    }
}