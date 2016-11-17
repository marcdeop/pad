import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.lang.String;
import java.util.Random;

public class ChatServer {
  public static void main(String[] args) throws Exception {
    final MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
    final ConcurrentHashMap<MySocket, String> socketClient 
      = new ConcurrentHashMap<MySocket, String>();

    while (true) {
      final MySocket s =  ss.accept();
      // Asign a random name to the client
      boolean nameFree = false;
      int random=0;
      while (!nameFree){
        random = new Random().nextInt(100);
        if(!socketClient.containsValue("Client"+random))
          nameFree=true;
      }
      socketClient.put(s,"Client"+random);
      System.out.println("Client"+random+" connected...");

      // We launch the thread that will process this client's messages
      new ServerClientThread(s,socketClient).start();
    }
  }

}
