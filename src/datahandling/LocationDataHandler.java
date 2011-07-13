package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class LocationDataHandler extends AbstractDataHandler {

	private PreparedStatement insertLocToLocationStmt = null;
	private PreparedStatement insertShotInStmt = null;

	DBConnector con;
	Cache cache;

	public LocationDataHandler() {

		super("Daten/locations.list", 264, 532964, "\t+");

		// get database connection
		con = DBConnector.getInstance();
		cache = Cache.getInstance();
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
		if (cache.movie.contains(title)){

			if (!cache.location.contains(location)) {

				// add an entry to the location table
				insertLocToLocationStmt.setString(1, country);
				insertLocToLocationStmt.setString(2, location);
				insertLocToLocationStmt.addBatch();
				cache.location.add(location);
			}

			// add also to the shotIn table
			insertShotInStmt.setString(1, title);
			insertShotInStmt.setString(2, location);
			insertShotInStmt.addBatch();
		}
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertLocToLocationStmt.executeBatch();
		con.connection.commit();
		
		insertShotInStmt.executeBatch();
		con.connection.commit();
		
		insertLocToLocationStmt.close();
		insertShotInStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertLocToLocationStmt = con.connection
				.prepareStatement("INSERT INTO Location (country, location)"
						+ "VALUES (?,?);");

		insertShotInStmt = con.connection
				.prepareStatement("INSERT INTO ShotIn (movie,location) VALUES (?,?);");
	}
}
