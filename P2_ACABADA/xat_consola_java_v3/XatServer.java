import util.ConnectionTable;
import util.MyServerSocket;
import util.MySocket;

public class XatServer {
    
    public static void main(String[] args){
        MyServerSocket ss = new MyServerSocket();
        ConnectionTable dicc = new ConnectionTable();
        System.out.println("Waiting clients...");

        while(true){
            MySocket sc = ss.accept();

            Thread handlerThread = new Thread(() -> {
                String my_nickname=sc.read();                
                String my_nickname_def = dicc.addParticipant(my_nickname, sc);
                System.out.println(my_nickname_def + " -> has joined");
                String content;
                while((content=sc.read())!=null){
                    dicc.broadcast(content, my_nickname_def);  //proteger el diccionario                 
                }
                System.out.println(my_nickname_def + " -> has left");
                dicc.removeParticipant(my_nickname_def);
                sc.close();
            });
            handlerThread.start();

        }
    }
}
