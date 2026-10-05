import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 6379);
        System.out.println("Connected to server!");
        OutputStream output =socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String[] commands ={"SET name Aditya", "GET name", "DELETE name", "GET name", "EXIT"};
        for(String command : commands){
            output.write((command + "\n").getBytes(StandardCharsets.UTF_8));
            if(command.equals("EXIT")){
                break;
            }
            String response = reader.readLine();
            System.out.println("Server response : " + response);
        }
    }
}