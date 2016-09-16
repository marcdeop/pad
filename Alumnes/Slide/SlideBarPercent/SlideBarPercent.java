import java.io.*;
import static java.lang.System.in;

public class SlideBarPercent {

  private static void setRaw() {
    ...
  }
  
  private static void unsetRaw() {
    ...
  }
  
  static final int RIGHT = 0, LEFT = 1;
  
  public static int readArrow() throws IOException {
    int ch;
    
    do {
      ch = in.read();
      ...
    } while (ch != '\r');
    return ch;
  }
  
  public static void main(String[] args) throws IOException {
    int arrow;
    ConsoleBar conBar = null;
    ConsolePercent conPer = null;
    Value value = null;
    
    try {
      setRaw();
      value = new Value();
      conBar = new ConsoleBar(value);
      ...
      conPer = new ConsolePercent(value);
      ...
      value.addObserver(conBar);
      value.addObserver(conPer);
      
      while ((arrow = readArrow()) != '\r')
        if (arrow == RIGHT)
          value.inc();
        else
          value.dec();
    } finally {
      unsetRaw();
      ...
    }
  }
}

