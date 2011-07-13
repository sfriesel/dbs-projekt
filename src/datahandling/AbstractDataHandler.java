package datahandling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import database.DBConnector;

/***
 * 
 * @author alexa
 * 
 */
public abstract class AbstractDataHandler {

	private BufferedReader reader = null;

	// pattern the line is splitted
	private String pattern;
	private String filename;
	// the numbers of lines, which are skipped in the beginning
	private int skipLineNumber;
	// the last line, which is parsed
	private int endLineNumber;
	
	public int counter = 0;

	private int commitCounter = 0;
	private static final int transaction = 5000;

	public AbstractDataHandler(String filename, int skipLineNumber,
			int endLineNumber, String pattern) {
		this.filename = filename;
		this.skipLineNumber = skipLineNumber;
		this.endLineNumber = endLineNumber;
		this.pattern = pattern;
	}

	/**
	 * Open the file to be parsed and create a bufferedReader for that file.
	 * 
	 * @param filename
	 *            The file to be parsed.
	 */
	protected void open(String filename) throws UnsupportedEncodingException,
			FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				filename), "ISO-8859-15"));
	}

	/**
	 * Skip some useless lines in the beginning of the file.
	 * 
	 * @param lineNumber
	 *            The number of lines to be skipped.
	 */
	protected void skipHeader(int skipLineNumber) throws IOException {
		for (int i = 0; i < skipLineNumber; i++) {
			reader.readLine();
			counter++;
		}
	}

	/**
	 * Parsing the whole file line by line until is reaches the end. Each line
	 * is splitted at a certain pattern.
	 */
	public void parse() throws IOException, SQLException {

		open(filename);
		skipHeader(skipLineNumber);
		prepareStatements();

		DBConnector con = DBConnector.getInstance();

		for (int i = 0; i < (endLineNumber - skipLineNumber); i++) {

			String[] arrayLine = reader.readLine().split(pattern);
			counter++;
			insertDB(arrayLine);

			commitCounter++;
			if (commitCounter == transaction) {

				con.connection.commit();
				commitCounter = 0;
			}
		}

		if (commitCounter >= 0) {
			con.connection.commit();
		}

		closeStatements();
		counter = 0;
	}

	/**
	 * Inserts one line into the corresponding database tables. If necessary
	 * modifies the data.
	 * 
	 * @param arrayLine
	 *            The line to be added to the database.
	 */
	protected abstract void insertDB(String[] arrayLine) throws SQLException;

	/**
	 * Prepares the Statements to be committed to the DB. Individual statements
	 * should be created in here.
	 * 
	 * @throws SQLException
	 */
	protected abstract void prepareStatements() throws SQLException;

	/**
	 * This method should be called after the parsing is finished. All
	 * statements should be closed here.
	 * 
	 * @throws SQLException
	 */
	protected abstract void closeStatements() throws SQLException;
}
