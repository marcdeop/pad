import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread;

public class EchoClient {
  public static void main(String[] args) throws Exception {
    final MySocket sc = new MySocket(args[0], Integer.parseInt(args[1]));

    // Keyboard thread
    new Thread() {
      public void run() {
      String line;
      BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
      try {
        System.out.println("Starting keyboard thread...");
        while ((line = kbd.readLine()) != null ) {
            sc.println(line);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      }
    }.start();

    // Console thread
    new Thread() {
      public void run() {
        String line;
        try {
          System.out.println("Starting Console thread...");
          while ((line = sc.readLine()) != null )
            System.out.println(line);
        } catch(Exception e){
          e.printStackTrace();
        }
      }
    }.start();
  }
}
