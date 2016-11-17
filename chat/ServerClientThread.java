import java.lang.Thread;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Iterator;
import java.lang.String;

public class ServerClientThread extends Thread {

  private MySocket socket;
  private ConcurrentHashMap<MySocket, String> socketClientMap;

  public ServerClientThread(MySocket socket, ConcurrentHashMap<MySocket, String> socketClientMap) {
    this.socket = socket;
    this.socketClientMap = socketClientMap;
  }

  public void run() {
    String line;
    try {
      while ((line = socket.readLine()) != null ) {
        Iterator<Map.Entry<MySocket,String>> entries = socketClientMap.entrySet().iterator();
          while (entries.hasNext()) {
              Entry thisEntry = (Entry) entries.next();
              MySocket key = (MySocket) thisEntry.getKey();
              String value = (String) thisEntry.getValue();
              if ( socket != key )
                key.println(value+":"+line);
          }
      }
      System.out.println(socketClientMap.get(socket)+" disconnected...");
      socketClientMap.remove(socket);
      socket.close();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
