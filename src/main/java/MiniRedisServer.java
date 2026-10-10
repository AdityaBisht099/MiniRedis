import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class MiniRedisServer {
    static void handleClient(
            Socket socket,
            KeyValueStore store,
            RespParser parser,
            CommandHandler handler,
            RespEncoder encoder,
            PersistenceManager persistence) throws Exception {
        try(
                Socket clientSocket = socket;
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream()
        ){
            while(true){
                String[] parts = parser.parse(input);
                System.out.println(Arrays.toString(parts));
                RespValue response = handler.execute(parts, store, persistence);
                String encodedResponse = encoder.encode(response);
                output.write(encodedResponse.getBytes(StandardCharsets.UTF_8));
                output.flush();
                System.out.println(response);
                if(parts.length == 1 && parts[0].equals("EXIT")){
                    break;
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6379);
        KeyValueStore store = new KeyValueStore();
        CommandHandler handler = new CommandHandler();
        RespParser parser = new RespParser();
        RespEncoder encoder = new RespEncoder();
        PersistenceManager persistence = new PersistenceManager();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while(true){
            Socket socket = serverSocket.accept();
            executor.submit(() -> {
                try{
                    handleClient(socket, store, parser, handler, encoder, persistence);
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}