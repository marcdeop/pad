import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MySocket {

  private Socket socket;

  public MySocket(Socket socket) throws Exception {
    this.socket = socket;
  }

  public MySocket(String host, int port) throws Exception {
    socket = new Socket(host,port);
  }

  public void close() throws Exception {
    socket.close();
  }

  /**
   * Reads a line from the socket
   *
   * @throws Exception
   * @return String
   **/
  public String readLine() throws Exception {
    BufferedReader line = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    return line.readLine();
  }

  /**
   * Writes a line on the socket
   *
   * @throws Exception
   **/
  public void println(String line) throws Exception {
    PrintWriter out = new PrintWriter(socket.getOutputStream());
    out.println(line);
    out.flush();
  }

  /**
   * Disables the output strem for the socket
   *
   * @throws Exception
   **/
  public void shutdownOutput() throws Exception {
    socket.shutdownOutput();
  }

}
