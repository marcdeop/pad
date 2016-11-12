import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClientSwing implements ActionListener {
    JFrame chatFrame;
    JPanel chatPanel;
    JTextField inputField;
    static JTextArea textArea;
    static MySocket sc;

    public ChatClientSwing() {
        //Create and set up the window.
        chatFrame = new JFrame("Chat Client made in Swing");
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the panel.
        chatPanel = new JPanel(new GridLayout(2,2));

        //Add the widgets.
        addWidgets();

        //Add the panel to the window.
        chatFrame.getContentPane()
          .add(chatPanel, BorderLayout.CENTER);

        //Display the window.
        chatFrame.pack();
        chatFrame.setVisible(true);
    }

    /**
     * Create and add the widgets.
     */
    private void addWidgets() {
    // Create widgets.
    textArea = new JTextArea();
    inputField = new JTextField();

    // Listen to events from the inputField button.
    inputField.addActionListener(this);

    // Add widgets to container.
    chatPanel.add(textArea);
    chatPanel.add(inputField);

    }

    public void actionPerformed(ActionEvent event) {
      // Gets the text from inputField (clearing it afterwards), adds the 
      // message to the text area and writes it to the socket to send it
      // to the server
      String message = inputField.getText();
      inputField.setText("");
      textArea.append(message+"\n");
      try {
        sc.println(message);
      } catch(Exception e){
        e.printStackTrace();
      }
    }

    private static void createAndShowGUI() throws Exception {
        //Set the look and feel.
        try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        ChatClientSwing converter = new ChatClientSwing();

        // Output thread
        new Thread() {
          public void run() {
            String line;
            try {
              while ((line = sc.readLine()) != null )
                textArea.append(line+"\n");
              sc.close();
            } catch(Exception e){
              e.printStackTrace();
            }
          }
        }.start();
    }

    public static void main(String[] args) throws Exception {
      // We initiate the connection (harcoded for now)
      sc = new MySocket("localhost", 2000);

      //Schedule a job for the event-dispatching thread:
      //creating and showing this application's GUI.
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            try {
              createAndShowGUI();
            } catch(Exception e){
              e.printStackTrace();
            }
          }
      });
    }
}
