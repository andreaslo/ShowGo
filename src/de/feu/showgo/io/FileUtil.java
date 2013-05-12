package de.feu.showgo.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A utility class providing methods for handling files.
 * 
 */
public class FileUtil {

	/**
	 * This method reads the content of a file into a string.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "cp1252"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}

			return sb.toString();
		} finally {
			br.close();
		}
	}

}
