package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnecter holds exactly one database connection. Its follows the Singleton
 * design pattern.
 * 
 * @author alexa
 * 
 */
public class DBConnector {

	private static DBConnector instance = null;
	private String driver = "org.postgresql.Driver";
	public Connection connection = null;
	// --------------------------------------------------------------------------
	private String host = "localhost";
	private String port = "5432";
	private String database = "movies";
	private String user = "alexa";
	private String password = "dinkel";
	// --------------------------------------------------------------------------

	private DBConnector() {
	}

	public static DBConnector getInstance() {
		if (instance == null) {
			instance = new DBConnector();
			
			instance.loadJdbcDriver();
			instance.openConnection();
		}
		return instance;
	}

	/**
	 * Close the database connection.
	 */
	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error while closing database connection.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates the url-string for the connection.
	 * 
	 * @return the url-string
	 */
	private String getUrl() {
		return ("jdbc:postgresql:"
				+ (host != null ? ("//" + host)
						+ (port != null ? ":" + port : "") + "/" : "") + database);
	}

	/**
	 * Loading the JDBC driver.
	 */
	private void loadJdbcDriver() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Error while loading the JDBC driver.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Opening the connection
	 */
	private void openConnection() {
		try {
			connection = DriverManager.getConnection(getUrl(), user, password);
		} catch (SQLException e) {
			System.out.println("Error while opening a connnection.");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
