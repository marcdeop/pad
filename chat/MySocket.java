import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class MySocket {

  private Socket socket;
  private PrintWriter out;
  private BufferedReader line;

  public MySocket(Socket socket) throws Exception {
    this.socket = socket;
    this.out = new PrintWriter(socket.getOutputStream());
    this.line = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public MySocket(String host, int port) throws Exception {
    socket = new Socket(host,port);
    out = new PrintWriter(socket.getOutputStream());
    line = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
    return line.readLine();
  }

  /**
   * Writes a line on the socket
   *
   * @throws Exception
   **/
  public void println(String line) throws Exception {
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
