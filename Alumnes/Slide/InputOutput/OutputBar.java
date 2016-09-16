import java.util.*;
import java.io.*;

class OutputBar implements Output {
	static final char BLOCK = ...;
	int cmax;

	OutputBar() {
		// get max columns
		cmax = ...;
		...
	}

	int getMax() {
		return cmax;
	}

	public void update(int command) {
		switch (command) {
		case Input.INC:
			System.out.print(BLOCK);
			break;
		case Input.DEC:
			// delete char to the left
			break;
		case Input.BELL:
			System.out.print('\007');
			break;
		}
	}

	void close() {
		...
	}
}
