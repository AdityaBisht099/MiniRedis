import java.io.InputStream;
import java.nio.charset.StandardCharsets;
public class RespDecoder {
    private String readLine(InputStream input) throws Exception {
        StringBuilder line = new StringBuilder();
        int current;
        int previous = -1;
        while ((current = input.read()) != -1) {
            if (previous == '\r' && current == '\n') {
                line.setLength(line.length() - 1);
                return line.toString();
            }
            line.append((char) current);
            previous = current;
        }
        throw new Exception("Connection closed");
    }
    private byte[] readBytes(InputStream input, int length) throws Exception {
        byte[] data = new byte[length];
        int offset = 0;
        while (offset < length) {
            int bytesRead = input.read(data, offset, length - offset);
            if (bytesRead == -1) {
                throw new Exception("Connection closed");
            }
            offset += bytesRead;
        }
        return data;
    }
    public String decode(InputStream input) throws Exception {
        String line = readLine(input);
        if (line.charAt(0) == '+') {
            return line.substring(1);
        }
        if (line.charAt(0) == '-') {
            return line.substring(1);
        }
        if (line.charAt(0) == '$') {
            int length = Integer.parseInt(line.substring(1));
            if (length == -1) {
                return null;
            }
            byte[] data = readBytes(input, length);
            readLine(input);
            return new String(data, StandardCharsets.UTF_8);
        }
        return line;
    }
}