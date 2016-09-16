/*
 * execute xterm with:
 *   xterm -fa "Monospace" -fs 10 -xrm 'XTerm*allowWindowOps: true'
 */

import java.io.*;

public class SlideInputOutput {

	public static void main(String[] args) throws IOException {
		int i;
		InputMouse in = null;
		OutputBar out = null;
		Value value = null;

		try {
			in = new InputMouse();
			out = new OutputBar();
			value = new Value(out.getMax());

			while ((i = in.readInput()) != Input.END)
				if (i == Input.INC)
					out.update(value.inc());
				else
					out.update(value.dec());
		} finally {
			in.close();
			out.close();
		}
	}
}
