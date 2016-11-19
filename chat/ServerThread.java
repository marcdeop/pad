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

  /**
   * Sends message to all connected clients
   * @param message message to send to all clients
   *
   * @throws Exception
   **/
  public void sendToAllClients(String message) throws Exception {
    Iterator<MySocket> sockets = socketClientMap.keySet().iterator();
      while (sockets.hasNext()) {
        MySocket client = sockets.next();
        client.println(message);
      }
  }

  public void run() {
    String line;
    try {
      String initialNick = getClientNick(socket);

      // We notify all clients of this client connection
      sendToAllClients("/add "+ initialNick);

      // notify this client of all the other clients
      Iterator<String> nicks = socketClientMap.values().iterator();
      while (nicks.hasNext()) {
        String nick = nicks.next();
        if ( ! initialNick.equals(nick) ) { // don't notify ourselves
          socket.println("/add " + nick);
        }
      }

      // process commands and messages
      while ((line = socket.readLine()) != null ) {
        if ( line.startsWith("/nick " )) {
          String nick = line.substring(6);
          if(!socketClientMap.contains(nick)) {
            String previousNick = getClientNick(socket);
            setClientNick(socket,nick);
            sendToAllClients("/update "+ previousNick + " " + nick);
          }
        }
        else {
          String clientSendingMessage = getClientNick(socket);
          sendToAllClients(clientSendingMessage+": "+line);
        }
      }

      String nick = getClientNick(socket);
      System.out.println(nick+" disconnected...");
      socketClientMap.remove(socket);
      socket.close();
      // notify all clients of this client disconnection
      sendToAllClients("/remove "+ nick);
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
