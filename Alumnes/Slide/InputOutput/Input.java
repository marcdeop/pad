import java.io.*;

public abstract class Input {

	protected void setRaw() {
		...
	}

	protected void unsetRaw() {
		...
	}
	
	static final int INC = 0, DEC = 1, END = 2, BELL = 3;

	abstract public int readInput() throws IOException;
}
