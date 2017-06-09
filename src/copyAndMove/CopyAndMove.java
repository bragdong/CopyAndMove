package copyAndMove;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
//import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class CopyAndMove {

	public static void writeFile(byte[] bs, String destination) throws Exception {

		try {
			Path path = Paths.get(destination);
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			RandomAccessFile f = new RandomAccessFile(destination, "rw");
			f.write(bs);
			f.close();
			return;
		} catch (IOException ex) {
			System.out.println(ex);
			return;
		}

	}

	public static byte[] readFile(String source) throws Exception {

		byte[] buf = new byte[100];
		byte[] data = null;
		int dataIdx = 0;

		RandomAccessFile f = new RandomAccessFile(source, "r");
		data = new byte[(int) f.length()];
		try {
			while (true) {
				int nBytes = f.read(buf);
				if (nBytes == -1) {
					break;
				}
				for (int i = 0; i < nBytes; i++) {
					data[dataIdx] = buf[i];
					dataIdx++;
				}
			}
			f.close();
			return data;
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found");
			System.out.println(ex);
			return data;
		}

	}

	public static void deleteFile(String source) {
		File f = new File(source);
		f.delete();
	}

	public static void main(String[] args) throws Exception {

		if ((args.length != 3) || !(args[0].toLowerCase().equals("cp") || args[0].toLowerCase().equals("mv"))) {
			System.out.println("Must supply cp or mv command and then a source and destination file");
			return;
		}

		String operation = args[0].toLowerCase();
		String source = args[1];
		String destination = args[2];
		byte[] data = null;

		try {
			data = CopyAndMove.readFile(source);
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found");
			System.out.println(ex);
			return;
		} catch (IOException ex) {
			System.out.println(ex);
			return;
		}

		// Charset cs = Charset.forName("UTF-8");
		// String s = new String(data, cs);
		// System.out.println(s);

		try {
			CopyAndMove.writeFile(data, destination);
			if (operation.equals("mv")) {
				CopyAndMove.deleteFile(source);
				System.out.println("Move completed.");
			} else {
				System.out.println("Copy completed.");
			}
		} catch (Exception ex) {
			System.out.println("Error trying to write file.");
			System.out.println(ex);
			return;
		}
	}
}