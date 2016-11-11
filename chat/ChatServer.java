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
      new Thread() {
        public void run() {
          String line;
          try {
            while ((line = s.readLine()) != null ) {
              // We need to write to all clients
                Iterator entries = socketClient.entrySet().iterator();
                while (entries.hasNext()) {
                    Entry thisEntry = (Entry) entries.next();
                    MySocket key = (MySocket) thisEntry.getKey();
                    String value = (String) thisEntry.getValue();
                    if ( s != key )
                      key.println(socketClient.get(s)+":"+line);
                }
            }
            System.out.println(socketClient.get(s)+" disconnected...");
            socketClient.remove(s);
            s.close();
          } catch(Exception e){
            e.printStackTrace();
          }
          }
      }.start();
    }
  }

}
