import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClientSwing implements ActionListener {
    JFrame chatFrame;
    JPanel chatPanel;
    JTextField inputField;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    static JTextArea textArea;
    static MySocket sc;

    public ChatClientSwing() {
      // Create and set up the window.
      JFrame frame = new JFrame("Xat");
      frame.setLayout(new BorderLayout(5,5));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
      JPanel out = new JPanel();
      out.setLayout(new BoxLayout(out,BoxLayout.LINE_AXIS));
      out.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
      textArea = new JTextArea(20,30);
      textArea.setEditable(false);
      out.add(new JScrollPane(textArea));
      listModel = new DefaultListModel<>();
      list = new JList<>(listModel);
      JScrollPane listScrollPane = new JScrollPane(list);
      out.add(listScrollPane, BorderLayout.CENTER);

      // Create an input JPanel and add a JTextField(25) and a JButton
      JPanel inp = new JPanel();
      inp.setLayout(new BoxLayout(inp,BoxLayout.LINE_AXIS));
      inputField = new JTextField();
      JButton button = new JButton("Send");
      inp.add(inputField);
      inp.add(button);

      // Listen to events from the inputField button.
      inputField.addActionListener(this);

      // add panels to main frame
      frame.add(out, BorderLayout.CENTER);
      frame.add(inp, BorderLayout.PAGE_END);

      //Display the window centered.
      frame.setSize(500,400);
      //frame.pack();

      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
      // Gets the text from inputField (clearing it afterwards), adds the 
      // message to the text area and writes it to the socket to send it
      // to the server
      String message = inputField.getText();
      inputField.setText("");
      try {
        sc.println(message);
      } catch(Exception e){
        e.printStackTrace();
      }
    }

    private static void createAndShowGUI() throws Exception {
        //Set the look and feel.
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

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
