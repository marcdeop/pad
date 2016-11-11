import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread;

public class ChatClient {
  public static void main(String[] args) throws Exception {
    final MySocket sc = new MySocket(args[0], Integer.parseInt(args[1]));

    // Input thread
    new Thread() {
      public void run() {
      String line;
      BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
      try {
        while ((line = kbd.readLine()) != null ) 
            sc.println(line);
        sc.shutdownOutput();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      }
    }.start();

    // Output thread
    new Thread() {
      public void run() {
        String line;
        try {
          while ((line = sc.readLine()) != null )
            System.out.println(line);
          sc.close();
        } catch(Exception e){
          e.printStackTrace();
        }
      }
    }.start();
  }
}
