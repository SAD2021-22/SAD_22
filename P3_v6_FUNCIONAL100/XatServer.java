import util.ConnectionTable;
import util.MyServerSocket;
import util.MySocket;
import java.io.IOException;

public class XatServer {
      

    public static void main(String[] args) {
        try {
            Reactor reactor  = new Reactor();
            new Thread(reactor).start();
            System.out.println("Waiting for a connection...");
        } catch (IOException e) {
            System.out.println("An error has happened");
        }
    }
}

