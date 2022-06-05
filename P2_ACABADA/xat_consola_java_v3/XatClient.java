
import java.io.BufferedReader;
import java.io.InputStreamReader;

import util.*;
public class XatClient {
    public static String my_nickname;
    public static void main(String[] args){        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Write your username: ");

        try{
            my_nickname = in.readLine();
            System.out.println("Hello " + my_nickname);
        }catch(Exception e){
            System.out.println("Error reading your message");
        }

        MySocket sc = new MySocket(my_nickname);        
        
        Thread socketThread = new Thread(() -> {
            try{
                String line;
                while ((line = in.readLine()) != null){ //especificat a l'enunciat
                    sc.write(line);                                        
                }
            }catch (Exception e){
                System.out.println("Error reading your message");
            }
        });
        socketThread.start();

        Thread keyboardThread =new Thread(() -> {
            String line;
            while((line = sc.read())!= null){ //especificat a l'enunciat
                System.out.println(line);
            }
        });  
        keyboardThread.start();
    }
}
