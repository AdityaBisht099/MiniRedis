import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommandParser command = new CommandParser();
        KeyValueStore store = new KeyValueStore();
        CommandHandler handler = new CommandHandler();
        PersistenceManager persistence = new PersistenceManager();
        while(true){
            String input = sc.nextLine();
            String[] str = command.parse(input);
            RespValue response = null;
            try{
                response = handler.execute(str, store,persistence);
            }catch (IOException e){
                System.out.println("Could not save command: " + e.getMessage());
                continue;
            }
            if("EXIT".equals(input)){
                break;
            }
            System.out.println(response.getValue());
        }
    }
}