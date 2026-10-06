import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class RespParserTest {
    public static void main(String[] args) throws Exception {
        String resp = "*3\r\n" +
                "$3\r\n" +
                "SET\r\n" +
                "$4\r\n" +
                "name\r\n" +
                "$5\r\n" +
                "Hello\r\n";
        ByteArrayInputStream input = new ByteArrayInputStream(resp.getBytes(StandardCharsets.UTF_8));
        RespParser parser = new RespParser();
        String[] parts = parser.parse(input);
        System.out.println(Arrays.toString(parts));
    }
}