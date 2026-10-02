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
    static void handleClient(Socket socket, KeyValueStore store, CommandParser parser, CommandHandler handler) throws Exception {
        try(Socket clientSocket = socket; BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream output = clientSocket.getOutputStream()){
            while(true) {
                String command = reader.readLine();
                if (command == null) {
                    break;
                }
                String[] parts = parser.parse(command);
                System.out.println(Arrays.toString(parts));
                String response = handler.execute(parts, store);
                output.write((response + "\n").getBytes(StandardCharsets.UTF_8));
                System.out.println(response);
                if("EXIT".equals(response)){
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
        CommandParser parser = new CommandParser();
        CommandHandler handler = new CommandHandler();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while(true) {
            Socket socket = serverSocket.accept();
            executor.submit(() -> {
                try {
                    handleClient(socket, store, parser, handler);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}