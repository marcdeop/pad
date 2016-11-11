public class EchoServer {
  public static void main(String[] args) throws Exception {
    final MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));

    while (true) {
      final MySocket s =  ss.accept();
      System.out.println("Accepted a connection, creating new thread...");
      new Thread() {
        public void run() {
          String line;
          try {
            while ((line = s.readLine()) != null ) 
              s.println("SERVER ECHO: "+line);
            s.close();
          } catch(Exception e){
            e.printStackTrace();
          }
          }
      }.start();
    }
  }

}
