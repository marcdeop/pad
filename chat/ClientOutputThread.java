import java.lang.Thread;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientOutputThread extends Thread {

  private MySocket socket;

  public ClientOutputThread(MySocket socket) {
    this.socket = socket;
  }

  public void run() {
    String line;
    try {
      while ((line = socket.readLine()) != null )
        System.out.println(line);
      socket.close();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}

