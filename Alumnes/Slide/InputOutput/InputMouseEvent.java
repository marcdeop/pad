import java.io.*;
import java.nio.*;
import java.nio.channels.*;
//import org.apache.poi.util.*;

public class InputMouseEvent extends Input {

	static final int BTN_LEFT = 0x110, BTN_MIDDLE = 0x112, BTN_RIGHT = 0x111;
	/*ReadableByteChannel*/MyDataInputStream in;
	ByteBuffer bin;

	/*static class InputEvent {
		long time;
		short type;
		short code;
		int value;
	}*/
	
	static class MyDataInputStream extends DataInputStream {
		MyDataInputStream(InputStream in) {
			super(in);
		}
		short readShortLittle() throws IOException {
			...
			//return LittleEndian.readShort(in);
		}
		int readIntLittle() throws IOException {
			...
			//return LittleEndian.readInt(in);
		}
		long readLongLittle() throws IOException {
			...
			//return LittleEndian.readInt(in);
		}
	}
	
	InputMouseEvent() throws FileNotFoundException {
		in = new MyDataInputStream(new BufferedInputStream(new FileInputStream("/dev/input/event5")));
		/*in = Channels.newChannel(new FileInputStream("/dev/input/event5"));
		bin = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN); // sizeof(struct input_event)*/
	}

	void close() {
	}

	public int readInput() throws IOException {
		short code, type;
		int value;

		do {
			/*in.read(bin);
			bin.flip();
			bin.getLong();
			type = bin.getShort();
			code = bin.getShort();
			value = bin.getInt();
			bin.clear();*/
			/*LittleEndian.readLong(in);
			type = LittleEndian.readShort(in);
			code = LittleEndian.readShort(in);
			value = LittleEndian.readInt(in);*/
			in.readLongLittle();
			type = in.readShortLittle();
			code = in.readShortLittle();
			value = in.readIntLittle();
			if (type != 1 || value != 1) continue; // if not EV_KEY or key pressed skip

			switch (code) {
				case BTN_LEFT:
					return DEC;
				case BTN_RIGHT:
					return INC;
			}
		} while (code != BTN_MIDDLE);
		return END;
	}
}
