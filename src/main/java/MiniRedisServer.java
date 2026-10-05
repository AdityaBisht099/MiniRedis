import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class MiniRedisServer {
    static void handleClient(Socket socket, KeyValueStore store, RespParser parser, CommandHandler handler, RespEncoder encoder) throws Exception {
        try(Socket clientSocket = socket; BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream output = clientSocket.getOutputStream()){
            while(true) {
                String[] parts = parser.parse(reader);
                System.out.println(Arrays.toString(parts));
                RespValue response = handler.execute(parts, store);
                String encodedResponse = encoder.encode(response);
                output.write(encodedResponse.getBytes(StandardCharsets.UTF_8));
                System.out.println(response);
                if (parts.length == 1 && parts[0].equals("EXIT")) {
                    break;
                }
        }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6379);
        KeyValueStore store = new KeyValueStore();
        CommandHandler handler = new CommandHandler();
        RespParser parser = new RespParser();
        RespEncoder encoder = new RespEncoder();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while(true) {
            Socket socket = serverSocket.accept();
            executor.submit(() -> {
                try {
                    handleClient(socket, store, parser, handler, encoder);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}