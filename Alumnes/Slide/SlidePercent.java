import java.io.*;
import static java.lang.System.in;

public class SlidePercent {

  private static void setRaw() {
    // put terminal in raw mode
  }
  
  private static void unsetRaw() {
    // restore terminal to cooked mode
  }
  
  static final int RIGHT = 0, LEFT = 1;
  
  public static int readArrow() throws IOException {
    int ch;
    
    do {
      // read arrow key
      ch = in.read();
      ...
    } while (ch != '\r');
    return ch;
  }
  
  public static void main(String[] args) throws IOException {
    int arrow;
    ConsolePercent con = null;
    Value value = null;
    
    try {
      setRaw();
      value = new Value();
      con = new ConsolePercent(value);
      ...
      value.addObserver(con);
      
      while ((arrow = readArrow()) != '\r')
        if (arrow == RIGHT)
          value.inc();
        else
          value.dec();
    } finally {
      unsetRaw();
      // clean-up console
    }
  }
}

