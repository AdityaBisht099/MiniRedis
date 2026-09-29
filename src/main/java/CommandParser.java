public class CommandParser {
    public String[] parse(String str){
        String[] parts = str.split("\\s+");
        return parts;
    }
}