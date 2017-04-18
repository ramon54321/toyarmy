package org.toyarmy.graphics.rendering.utilities;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.*;
import java.nio.ByteBuffer;

public class FileLoader {

	public static String loadFileToString(String filename){
		
		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
		
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String contentLine = br.readLine();
			while (contentLine != null){
				sb.append(contentLine + "\n");
				contentLine = br.readLine();
			}
			
			br.close();
			
		} catch (Exception e) {
			System.err.println("Loading file to string failed!");
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
