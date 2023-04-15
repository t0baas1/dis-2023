package de.dis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A small helper class for reading data
 */
public class FormUtil {
	/**
	 * Reads a string from standard input
	 * @param label the text line shown before the input
	 * @return the read input line
	 */
	public static String readString(String label) {
		String ret = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.print(label+": ");
			ret = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * Reads an integer from the standard input
	 * @param label the text line shown before the input
	 * @return the read Integer
	 */
	public static int readInt(String label) {
		int ret = 0;
		boolean finished = false;

		while(!finished) {
			String line = readString(label);
			
			try {
				ret = Integer.parseInt(line);
				finished = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid Input: Please input a number!");
			}
		}
		
		return ret;
	}
}
