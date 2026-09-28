public class Main {
    public static void main(String[] args) {
        String input1 = "SET name Aditya";
        String input2 = "GET name";
        String input3 = "DELETE name";
        CommandParser command = new CommandParser();
        KeyValueStore store = new KeyValueStore();
        String[] str1 = command.parse(input1);
        if(str1[0].equals("SET")){
            store.set(str1[1], str1[2]);
        }
        String[] str2 = command.parse(input2);
        if (str2[0].equals("GET")) {
            System.out.println(store.get(str2[1]));
        }
        String[] str3 = command.parse(input3);
        if (str3[0].equals("DELETE")) {
            store.delete(str3[1]);
        }
        System.out.println(store.get(str3[1]));
    }
}