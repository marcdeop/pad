import java.lang.StringBuffer;

public class Line {
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
  }

  /**
   * Sets position to the beggining of the line
   *
   * @throws Exception
   **/
  public void home() throws Exception {
    position = 0;
  }

  /**
   * Delets the character at the current position
   *
   * @throws Exception
   **/
  public void delCharacter() throws Exception {
    // We only remove if we are not at the end of the line
    if ( position != line.length() )
      line.deleteCharAt(position);
  }

  /**
   * Removes character at the previous position
   *
   * @throws Exception
   **/
  public void backspace() throws Exception {
    if ( position > 0 ) {
      line.deleteCharAt(position-1);
      position--;
    }
  }

  /**
   * Moves one position to the left
   *
   * @throws Exception
   **/
  public void moveLeft() throws Exception {
    if ( position > 0 )
      position--;
  }

  /**
   * Moves one position to the right
   *
   * @throws Exception
   **/
  public void moveRight() throws Exception {
    if ( position < line.length() )
      position++;
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
    }
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
