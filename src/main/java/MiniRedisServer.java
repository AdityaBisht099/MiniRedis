import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.io.OutputStream;
public class MiniRedisServer {
    static void handleClient(Socket socket, KeyValueStore store, CommandParser parser, CommandHandler handler) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream output = socket.getOutputStream();
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
    }
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6379);
        KeyValueStore store = new KeyValueStore();
        CommandParser parser = new CommandParser();
        CommandHandler handler = new CommandHandler();
        while(true) {
            Socket socket = serverSocket.accept();
            Thread clientThread = new Thread(() -> {
                try {
                    handleClient(socket, store, parser, handler);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            clientThread.start();
        }
    }
}