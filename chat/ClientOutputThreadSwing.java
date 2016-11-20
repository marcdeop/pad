import java.lang.Thread;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientOutputThreadSwing extends Thread {

  private ChatClientSwing gui;

  public ClientOutputThreadSwing(ChatClientSwing gui) {
    this.gui = gui;
  }

  private void addNickToList(final String nick) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          try {
            gui.getListModel().addElement(nick);
          } catch(Exception e){
            e.printStackTrace();
          }
        }
    });
  }

  private void removeNickFromList(final String nick) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          try {
            gui.getListModel().removeElement(nick);
          } catch(Exception e){
            e.printStackTrace();
          }
        }
    });
  }

  private void addToTextArea(final String line) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          try {
            gui.getTextArea().append(line);
          } catch(Exception e){
            e.printStackTrace();
          }
        }
    });
  }

  public void run() {
    String line;
    try {
      while ((line = gui.getSocket().readLine()) != null ) {
        if ( line.startsWith("/add " )) {
          addNickToList(line.substring(5));
        } else
        if ( line.startsWith("/remove " )) {
          removeNickFromList(line.substring(8));
        } else
        if ( line.startsWith("/update " )) {
          String[] nicks = line.substring(8).split("\\s");
          removeNickFromList(nicks[0]);
          addNickToList(nicks[1]);
        }
        else {
          addToTextArea(line+"\n");
        }
      }
      gui.getSocket().close();
    } catch(Exception e){
      e.printStackTrace();
    }

  }
}

