import java.lang.StringBuffer;
import java.util.Observable;

public class Line extends Observable {
  private boolean insertMode;
  private int position;
  private StringBuffer line;

  public Line(){
    insertMode = false;
    position = 0;
    line = new StringBuffer();
  }

  /**
   * Flaps insert mode and returns
   *
   * @throws Exception
   * @return boolean
   **/
  public boolean switchInsertMode() throws Exception {
    insertMode = !insertMode;
    setChanged();
    notifyObservers((Integer)Codes.INSERT);
    return insertMode;
  }

  /**
   * Gets current insertMode
   *
   * @throws Exception
   * @return boolean
   **/
  public boolean getInsertMode() throws Exception{
    return insertMode;
  }

  /**
   * Gets current position in the line
   *
   * @throws Exception
   * @return int
   **/
  public int getPosition() throws Exception {
    return position;
  }

  /**
   * Gets current size of the line
   *
   * @throws Exception
   * @return int
   **/
  public int getSize() throws Exception {
    return line.length();
  }

  /**
   * Sets desired insert mode and returns
   * @params mode
   *
   * @throws Exception
   * @return boolean
   **/
  public boolean setInsertMode( boolean mode) throws Exception {
    //ToDo: this doesn't implement the MVC yet
    insertMode = mode;
    return insertMode;
  }

  /**
   * Sets position to the end of the line
   *
   * @throws Exception
   **/
  public void end() throws Exception {
    position = line.length();
    setChanged();
    notifyObservers((Integer)Codes.END);
  }

  /**
   * Sets position to the beggining of the line
   *
   * @throws Exception
   **/
  public void home() throws Exception {
    setChanged();
    if ( position > 0 ) {
      position = 0;
      notifyObservers((Integer)Codes.HOME);
    }
    else
      notifyObservers((Integer)Codes.BELL);
  }

  /**
   * Delets the character at the current position
   *
   * @throws Exception
   **/
  public void delCharacter() throws Exception {
    setChanged();
    // We only remove if we are not at the end of the line
    if ( position != line.length() ) {
      line.deleteCharAt(position);
      notifyObservers((Integer)Codes.DEL);
    }
    else
      notifyObservers((Integer)Codes.BELL);
  }

  /**
   * Removes character at the previous position
   *
   * @throws Exception
   **/
  public void backspace() throws Exception {
    setChanged();
    if ( position > 0 ) {
      line.deleteCharAt(position-1);
      position--;
      notifyObservers((Integer)Codes.BACKSPACE);
    }
    else
      notifyObservers((Integer)Codes.BELL);
  }

  /**
   * Moves one position to the left
   *
   * @throws Exception
   **/
  public void moveLeft() throws Exception {
    setChanged();
    if ( position > 0 ) {
      position--;
      notifyObservers((Integer)Codes.LEFT);
    }
    else
      notifyObservers((Integer)Codes.BELL);
  }

  /**
   * Moves one position to the right
   *
   * @throws Exception
   **/
  public void moveRight() throws Exception {
    setChanged();
    if ( position < line.length() ) {
      position++;
      notifyObservers((Integer)Codes.RIGHT);
    }
    else
      notifyObservers((Integer)Codes.BELL);
  }

  /**
   * Behaveor depends on insertMode
   *
   * @throws Exception
   **/
  public void addCharacter(char character) throws Exception {
    if (insertMode)
      // insertMode behaveor
      ;
    else {
      line.insert(position,character);
      position++;
      setChanged();
      notifyObservers((Integer)((int)character));
    }
  }

  /**
   * Returns character at requested position
   * @param position usage...
   *
   * @throws Exception
   * @return char
   **/
  public char getCharacter(int position) throws Exception {
    return line.charAt(position);
  }

  /**
   * Converts to string
   *
   * @throws Exception
   **/
  public String string() throws Exception {
    return line.toString();
  }
}
