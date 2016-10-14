import java.io.Reader;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.io.IOException;

public class EditableBufferedReader extends BufferedReader {
  private static final boolean RAW=false;
  private static final boolean COOKED=true;
  private static final int     BACKSPACE=127;
  private static final int     CR=13;
  private static final int     SQUARE_BRAQUET=91;
  private static final int     EOI=-1; // End Of Input
  private static final int     LEFT=-2;
  private static final int     RIGHT=-3;
  private static final int     DEL=-4;
  private static final int     INSERT=-5;
  private static final int     HOME=-6;
  private static final int     END=-7;

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
      case 27:
        // superfluos as we only accept certain sequences
        switch ( character = super.read() ) {
          case SQUARE_BRAQUET:
            switch ( character = super.read() ) {
              case 68:
                character = LEFT;
                break;
              case 67:
                character = RIGHT;
                break;
              case 49:
                character = HOME;
                // We assume following reads will be 126 and 13 so
                // we discard them
                read(); read();
                break;
              case 51:
                character = DEL;
                // We assume following reads will be 126 and 13 so
                // we discard them
                read(); read();
                break;
              case 52:
                character = END;
                // We assume following reads will be 126 and 13 so
                // we discard them
                read(); read();
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
    int element = 0;
    boolean readMore = true;
    try {
      setRaw();
      while ( readMore ) {
        switch( element = read() ) {
          case EOI:
            readMore = false;
            break;
          case CR:
            readMore = false;
            break;
          case DEL:
            line.delCharacter();
            break;
          case BACKSPACE:
            line.backspace();
            break;
          case LEFT:
            line.moveLeft();
            break;
          case RIGHT:
            line.moveRight();
            break;
          case INSERT:
            line.switchInsertMode();
            break;
          case HOME:
            line.home();
            break;
          case END:
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
