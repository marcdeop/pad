import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread;

public class ChatClient {
  public static void main(String[] args) throws Exception {
    final MySocket sc = new MySocket(args[0], Integer.parseInt(args[1]));

    // Input thread
    new ClientInputThread(sc).start();

    // Output thread
    new ClientOutputThread(sc).start();
  }
}
