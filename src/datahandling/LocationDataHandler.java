package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class LocationDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/locations.list";
	static final private int lineNumber = 264;
	static final private String pattern = "\t+";

	private PreparedStatement insertLocToLocationStmt = null;

	DBConnector con;

	public LocationDataHandler() {

		super(filename, lineNumber, pattern);

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// ignore empty lines in the end
		if (arrayLine.length == 1) {
			return;
		}

		// only if the movie is released in 2010 or 2011, add it to the database
		if (DataHandlerUtils.isInTimeRange(arrayLine[0])) {

			// arrayLine[1] -> full location
			// arrrayLine[1].last -> Country

			String country = null;
			String location = null;

			country = arrayLine[1].substring(arrayLine[1].lastIndexOf(",") + 1)
					.trim();
			location = arrayLine[1];

			insertLocToLocationStmt.setString(1, country);
			insertLocToLocationStmt.setString(2, location);
			insertLocToLocationStmt.execute();

			// TODO: doppeltes einfügen von Werten verhindern? Wie?

			// TODO: Also fill the table with movie and locations relation.
			// insert into shotin: location_id and movie_title
		}
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertLocToLocationStmt.close();

	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertLocToLocationStmt = con.connection
				.prepareStatement("insert into locations"
						+ "(country, location)" + "VALUES (?,?);");
	}
}
