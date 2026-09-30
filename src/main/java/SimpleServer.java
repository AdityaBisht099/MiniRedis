import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6379);
        System.out.println("Server started...");
        System.out.println("Waiting for client...");
        Socket socket = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String command = reader.readLine();
        System.out.println(command);
        OutputStream output = socket.getOutputStream();
        output.write(command.getBytes(StandardCharsets.UTF_8));
    }
}
