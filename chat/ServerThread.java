import java.lang.Thread;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Iterator;
import java.lang.String;

public class ServerThread extends Thread {

  private MySocket socket;
  private ConcurrentHashMap<MySocket, String> socketClientMap;

  public ServerThread(MySocket socket, ConcurrentHashMap<MySocket, String> socketClientMap) {
    this.socket = socket;
    this.socketClientMap = socketClientMap;
  }

  /**
   * Gets the nick assigned to a client
   * @param socket we will get nick assigned to this socket
   *
   * @throws Exception
   * @return String
   **/
  public String getClientNick(MySocket socket) throws Exception {
    return socketClientMap.get(socket);
  }

  /**
   * Sets the nick for the client
   * @param socket we will set a nick for this socket
   * @param nick that we want to set
   *
   * @throws Exception
   **/
  public void setClientNick(MySocket socket, String nick) throws Exception {
    socketClientMap.replace(socket, nick);
  }

  public void run() {
    String line;
    try {
      while ((line = socket.readLine()) != null ) {
        String clientSendingMessage = getClientNick(socket);
        Iterator<Map.Entry<MySocket,String>> entries = socketClientMap.entrySet().iterator();
          while (entries.hasNext()) {
              Entry<MySocket,String> thisEntry = entries.next();
              MySocket key = thisEntry.getKey();
              String value = thisEntry.getValue();
              if ( socket != key )
                key.println(clientSendingMessage+": "+line);
          }
      }
      System.out.println(getClientNick(socket)+" disconnected...");
      socketClientMap.remove(socket);
      socket.close();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
