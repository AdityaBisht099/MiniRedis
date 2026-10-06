import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class RespClient {
    static String encode(String[] parts) {
        StringBuilder resp = new StringBuilder();
        resp.append("*").append(parts.length).append("\r\n");
        for(String part : parts){
            resp.append("$")
                    .append(part.getBytes(StandardCharsets.UTF_8).length)
                    .append("\r\n");
            resp.append(part).append("\r\n");
        }
        return resp.toString();
    }
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 6379);
        OutputStream output = socket.getOutputStream();
        InputStream input = socket.getInputStream();
        String[] parts = {"GET", "name"};
        String resp = encode(parts);
        output.write(resp.getBytes(StandardCharsets.UTF_8));
        RespDecoder decoder = new RespDecoder();
        String response = decoder.decode(input);
        System.out.println(response);
    }
}
