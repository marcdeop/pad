import java.io.*;
import static java.lang.System.in;

/*
 * Use xterm mouse reporting
 */
public class InputMouse extends Input {

	static final int MOUSE1 = 0, MOUSE2 = 1, MOUSE3 = 2;
	static final int SHIFT = 04, META = 010, CONTROL = 020;

	boolean isMOUSE(int ch) {
		return (ch & 0x00000020) == 32;
	}

	int MOUSE(int ch) {
		return ch & 0x0000001f;
	}

	InputMouse() {
		setRaw();
		// begin mouse tracking
		...
	}

	void close() {
		// end mouse tracking
		...
		unsetRaw();
	}

	public int readInput() throws IOException {
		int ch;

		do {
			ch = in.read();
			...
			if (!isMOUSE(ch))
				continue;
			...
			switch (MOUSE(ch)) {
			case MOUSE1:
				return DEC;
			//case MOUSE2:
			case MOUSE3:
				return INC;
			}
		} while (MOUSE(ch) != /*(MOUSE1 | CONTROL)*/MOUSE2);
		return END;
	}
}
