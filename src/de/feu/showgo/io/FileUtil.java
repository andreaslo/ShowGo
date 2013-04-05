package de.feu.showgo.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

	public static String readFile(File file) throws IOException{
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
