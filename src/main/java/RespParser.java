import java.io.InputStream;
import java.nio.charset.StandardCharsets;
public class RespParser {
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
    public String[] parse(InputStream input) throws Exception {
        String line = readLine(input);
        int count = Integer.parseInt(line.substring(1));
        String[] parts = new String[count];
        for (int i = 0; i < count; i++) {
            String lengthLine = readLine(input);
            int length = Integer.parseInt(lengthLine.substring(1));
            byte[] data = readBytes(input, length);
            String value = new String(data, StandardCharsets.UTF_8);
            parts[i] = value;
            readLine(input);
        }
        return parts;
    }
}