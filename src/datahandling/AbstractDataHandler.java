package datahandling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public abstract class AbstractDataHandler {

	BufferedReader reader = null;
	String pattern;
	
	public AbstractDataHandler(String filename, int lineNumber, String pattern){
		open(filename);
		skipHeader(lineNumber);
		this.pattern = pattern;
	}

	/**
	 * Open the file to be parsed and create a bufferedReader for that file.
	 * 
	 * @param filename
	 *            The file to be parsed.
	 */
	public void open(String filename) {

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "ISO-8859-15"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Encoding not supported.");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found.");
			e.printStackTrace();
		}
	}

	/**
	 * Skip some useless lines in the beginning of the file.
	 * 
	 * @param lineNumber
	 *            The number of lines to be skipped.
	 */
	public void skipHeader(int lineNumber) {
		for (int i = 0; i < lineNumber; i++) {
			try {
				reader.readLine();
			} catch (IOException e) {
				System.out
						.println("Error while skipping the header of the file.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parsing the whole file line by line until is reaches the end. Each line
	 * is splitted at a certain pattern.
	 */
	public void parse() {

		String stringLine = null;
		String[] arrayLine = null;

		try {
			while ((stringLine = reader.readLine()) != null) {
				arrayLine = stringLine.split(pattern);
				insertDB(arrayLine);
			}
		} catch (IOException e) {
			System.out.println("Error while parsing the file.");
			e.printStackTrace();
		}
	}

	/***
	 * Inserts one line into the corresponding database tables. If necessary
	 * modifies the data.
	 * 
	 * @param arrayLine
	 *            The line to be added to the database.
	 */
	protected abstract void insertDB(String[] arrayLine);
}
