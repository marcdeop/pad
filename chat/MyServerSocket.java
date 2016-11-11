import java.net.ServerSocket;

public class MyServerSocket {

  private final ServerSocket socket;

  public MyServerSocket() throws Exception {
    socket = new ServerSocket();
  }

  public MyServerSocket(int port) throws Exception {
    socket = new ServerSocket(port);
  }

  public MySocket accept() throws Exception{
    return new MySocket(socket.accept());
  }

}
