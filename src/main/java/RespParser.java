import java.io.BufferedReader;

public class RespParser {
    public String[] parse(BufferedReader reader) throws Exception {
        String line = reader.readLine();
        String countString = line.substring(1);
        int count = Integer.parseInt(countString);
        String[] parts = new String[count];
        for (int i = 0; i < count; i++) {
            String lengthLine = reader.readLine();
            String lengthString = lengthLine.substring(1);
            int length = Integer.parseInt(lengthString);
            String value = reader.readLine();
            parts[i] = value;
        }
        return parts;
    }
}