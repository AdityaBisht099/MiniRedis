import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        CommandParser command = new CommandParser();
        KeyValueStore store = new KeyValueStore();
        CommandHandler handler = new CommandHandler();
        while(true) {
            String input = sc.nextLine();
            String[] str = command.parse(input);
            String res = handler.execute(str, store);
            if("EXIT".equals(res)) break;
            System.out.println(res);
        }
    }
}