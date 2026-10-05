import java.io.BufferedReader;
import java.io.StringReader;
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
        BufferedReader reader =new BufferedReader(new StringReader(resp));
        RespParser parser =new RespParser();
        String[] parts = parser.parse(reader);
        System.out.println(Arrays.toString(parts));
    }
}