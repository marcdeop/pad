import java.io.Reader;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.io.IOException;

public class EditableBufferedReader extends BufferedReader {
  private static final boolean RAW=false;
  private static final boolean COOKED=true;

  public EditableBufferedReader(Reader in, int sz) {
    super(in, sz);
  }

  public EditableBufferedReader(Reader in) {
    super(in);
  }

  /**
   * Constructs the command to set raw/cooked mode
   * @param mode false means raw mode and true means cooked mode
   *
   * @throws Exception
   * @return String[]
   **/
  private String sttyCommand(boolean mode) throws Exception{
    return ("stty -f /dev/tty -echo "+(mode?"-raw":"raw"));
  }

  /**
   * Sets terminal into *raw* mode
   *
   * @throws Exception
   **/
  public void setRaw() throws Exception {
    // call stty raw in our terminal
    Runtime.getRuntime().exec(sttyCommand(RAW));
  }

  /**
   * Sets terminal into *cooked* mode
   *
   * @throws Exception
   **/
  public void unsetRaw() throws Exception {
    // call stty -raw in our terminal
    Runtime.getRuntime().exec(sttyCommand(COOKED));
  }

  /**
   * Reads the next character or key from the cursor
   *
   * @throws IOException
   **/
  public int read() throws IOException {
    int character = -1;
    switch ( character = super.read() ){
      case Codes.ESCAPE:
        // superfluos as we only accept certain sequences
        switch ( character = super.read() ) {
          case '[':
            switch ( character = super.read() ) {
              case 'D':
                character = Codes.LEFT;
                break;
              case 'C':
                character = Codes.RIGHT;
                break;
              case 'H':
                character = Codes.HOME;
                break;
              case '1':
                character = Codes.HOME;
                // We assume following reads will be 126
                read();
                break;
              case '3':
                character = Codes.DEL;
                // We assume following reads will be 126
                read();
                break;
              case '4':
                character = Codes.END;
                // We assume following reads will be 126
                read();
                break;
            }
        }
        break;
    }
    return character;
  }

  /**
   * Reads the line and allows editing it
   *
   * @throws IOException
   **/
  public String readLine() throws IOException {
    Line line = new Line();
    Console console = new Console(line);
    line.addObserver(console);
    int element = 0;
    boolean readMore = true;
    try {
      setRaw();
      while ( readMore ) {
        switch( element = read() ) {
          case Codes.EOI:
            readMore = false;
            break;
          case Codes.CR:
            readMore = false;
            line.addCharacter((char)element);
            break;
          case Codes.DEL:
            line.delCharacter();
            break;
          case Codes.BACKSPACE:
            line.backspace();
            break;
          case Codes.LEFT:
            line.moveLeft();
            break;
          case Codes.RIGHT:
            line.moveRight();
            break;
          case Codes.INSERT:
            line.switchInsertMode();
            break;
          case Codes.HOME:
            line.home();
            break;
          case Codes.END:
            line.end();
            break;
          default:
            line.addCharacter((char)element);
            break;
        }
      }
      unsetRaw();
    } catch(Exception e){
      e.printStackTrace();
    }

    // ToDO: This needs improvement... looks ugly
    try {
      return line.string();
    } catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

}
