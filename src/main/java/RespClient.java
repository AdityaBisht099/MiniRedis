import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RespClient {

    static String encode(String[] parts) {
        StringBuilder resp = new StringBuilder();
        resp.append("*").append(parts.length).append("\r\n");
        for (String part : parts) {
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
        RespDecoder decoder = new RespDecoder();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("mini-redis> ");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("EXIT")) {
                break;
            }
            String[] parts = command.trim().split("\\s+");
            String resp = encode(parts);
            output.write(resp.getBytes(StandardCharsets.UTF_8));
            output.flush();
            String response = decoder.decode(input);
            System.out.println(response);
        }
        socket.close();
        scanner.close();
    }

}