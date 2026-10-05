import java.io.BufferedReader;
public class RespDecoder {
    public String decode(BufferedReader reader) throws Exception {
        String line = reader.readLine();
        if (line.charAt(0) == '+') {
            return line.substring(1);
        }
        if (line.charAt(0) == '$') {
            int length = Integer.parseInt(line.substring(1));
            if (length == -1) {
                return null;
            }
            return reader.readLine();
        }
        if (line.charAt(0) == '-') {
            return line.substring(1);
        }
        return line;
    }
}