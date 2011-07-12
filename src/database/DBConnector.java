package database;

import java.sql.*;
import java.io.*;
import java.util.Scanner;
import datahandling.*;

/**
 * DBConnecter holds exactly one database connection. Its follows the Singleton
 * design pattern.
 * 
 * @author alexa
 * 
 */
public class DBConnector {
	private static DBConnector instance = null;
	private static String driver = "org.postgresql.Driver";
	private static String baseURL = "jdbc:postgresql:";
	private static String schemaPath = "Datenbankschema/scriptSQL";

	public Connection connection = null;

	private DBConnector(String host,
	                    String port,
	                    String database,
	                    String user,
	                    String password) {
		DBConnector.loadJdbcDriver();
		try {
			String url = getUrl(host, port, database);
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Error while opening a connection.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void configure(String host,
	                             String port,
	                             String database,
	                             String user,
	                             String password) {
		if(instance == null) {
			instance = new DBConnector(host, port, database, user, password);
		} else {
			System.err.println("Error: trying to reconfigure an existing database connection");
			Thread.currentThread().dumpStack();
			System.exit(1);
		}
	}

	public static DBConnector getInstance() {
		if (instance == null) {
			System.out.println("Error: database connection not configured before instantiation");
			Thread.currentThread().dumpStack();
			System.exit(1);
		}
		return instance;
	}

	/**
	 * Close the database connection.
	 */
	protected void finalize() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Error while closing database connection.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Execute the sql statements given in inputFile
	 * based on http://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
	 */
	private void executeSqlScript(String path) {
		File inputFile = new File(path);
		String delimiter = ";";

		// Create scanner
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile).useDelimiter(delimiter);
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

		// Loop through the SQL file statements
		Statement currentStatement = null;
		while(scanner.hasNext()) {

			// Get statement
			String rawStatement = scanner.next() + delimiter;
			try {
				// Execute statement
				currentStatement = connection.createStatement();
				currentStatement.execute(rawStatement);
			} catch(SQLException e) {
				System.err.println("Failed to execute sql statement: " + rawStatement);
				e.printStackTrace();
			} finally {
				// Release resources
				if(currentStatement != null) {
					try {
						currentStatement.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				currentStatement = null;
			}
		}
	}

	/**
	 * Drops all tables from the database and re-inserts all data through the
	 * data handlers.
	 */
	public void resetDB() throws IOException, SQLException {
		executeSqlScript(DBConnector.schemaPath);

		//TODO: run all data handlers here
		AbstractDataHandler handler;
		handler = new MovieDataHandler();
		handler.parse();
	}

	/**
	 * Creates the url-string for the connection.
	 * 
	 * @return the url-string
	 */
	private static String getUrl(String host, String port, String database) {
		String url = baseURL;
		if(host != null) {
			url += "//" + host + (port != null ? ":" + port : "") + "/";
		}
		url += database;
		return url;
	}

	/**
	 * Loading the JDBC driver.
	 */
	private static void loadJdbcDriver() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Error while loading the JDBC driver.");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
