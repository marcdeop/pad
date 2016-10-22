import java.lang.Integer;
import java.util.Observer;
import java.util.Observable;

public class Console implements Observer {
  private Line line;

  public Console(Line line){
    this.line = line;
  }

  public void update(Observable o, Object arg) {
    int character = ((Integer)arg).intValue();
    switch( character ) {
      case Codes.DEL:
        System.out.print("\033[P");
        break;
      case Codes.BACKSPACE:
        System.out.print("\010\033[P");
        break;
      case Codes.LEFT:
        System.out.print("\033[D");
        break;
      case Codes.RIGHT:
        System.out.print("\033[C");
        break;
      case Codes.INSERT:
        // ToDO: output for insert mode
        break;
      case Codes.HOME:
        System.out.print("\033[G");
        break;
      case Codes.END:
        // can't find a single sequence to go to the end of the line
        // thus doing it like this. THIS IS UGLY!!!
        try {
          System.out.print("\033["+(line.getPosition()+1)+"G");
        } catch(Exception e){
          e.printStackTrace();
        }
        break;
      case Codes.BELL:
        System.out.print('\007');
        break;
      default:
        System.out.print("\033[@"+(char)character);
        break;
    }

  }
}
