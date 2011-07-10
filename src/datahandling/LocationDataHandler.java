package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class LocationDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/locations.list";
	static final private int lineNumber = 264;
	static final private String pattern = "\t+";

	private PreparedStatement insertLocToLocationStmt = null;
	private PreparedStatement insertShotInStmt = null;
	private PreparedStatement selectLocationStmt = null;
	private PreparedStatement selectMovStmt = null;

	DBConnector con;

	public LocationDataHandler() {

		super(filename, lineNumber, pattern);

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// arrayLine[0] -> movie title
		// arrayLine[1] -> full location
		// arrrayLine[1].last -> Country

		String title = arrayLine[0];

		// ignore empty lines in the end, only movies released in 2010 or 2011
		if (arrayLine.length == 1 || !DataHandlerUtils.isInTimeRange(title)) {
			return;
		}

		String country = arrayLine[1].substring(
				arrayLine[1].lastIndexOf(",") + 1).trim();
		String location = arrayLine[1];

		// get the movie from the DB
		selectMovStmt.setString(1, title);
		ResultSet movRS = selectMovStmt.executeQuery();

		// is the movie in the DB?
		if (movRS.next()) {

			// get the location from the DB
			selectLocationStmt.setString(1, location);
			ResultSet locRS = selectLocationStmt.executeQuery();

			// is the location in the DB?
			if (!locRS.next()) {

				// add an entry to the location table
				insertLocToLocationStmt.setString(1, country);
				insertLocToLocationStmt.setString(2, location);
				insertLocToLocationStmt.execute();
			}

			// add also to the shotIn table
			insertShotInStmt.setString(1, title);
			insertShotInStmt.setString(2, location);
			insertShotInStmt.execute();
		}
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertLocToLocationStmt.close();
		insertShotInStmt.close();
		selectLocationStmt.close();
		selectMovStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertLocToLocationStmt = con.connection
				.prepareStatement("INSERT INTO Location (country, location)"
						+ "VALUES (?,?);");

		insertShotInStmt = con.connection
				.prepareStatement("INSERT INTO ShotIn (movie,location) VALUES (?,?);");

		selectLocationStmt = con.connection
				.prepareStatement("SELECT * FROM Location WHERE location = ?;");

		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");
	}
}
