import java.lang.Thread;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientInputThread extends Thread {

  private MySocket socket;

  public ClientInputThread(MySocket socket) {
    this.socket = socket;
  }

  public void run() {
    String line;
    BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
    try {
      while ((line = kbd.readLine()) != null ) 
          socket.println(line);
      socket.shutdownOutput();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

