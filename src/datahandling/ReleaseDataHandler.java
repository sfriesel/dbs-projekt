package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import database.DBConnector;

public class ReleaseDataHandler extends AbstractDataHandler {

	private PreparedStatement updateMovStmt;
	private PreparedStatement selectMovStmt;

	private final DBConnector con;

	private String currentMovie = "";
	private boolean isUSA = false;

	public ReleaseDataHandler() {
		super("Daten/release-dates.list", 14, 2304630, "\t+");

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// arrayLine[0] -> title
		// arrayLine[1] -> release+country
		// arrayLine[2] -> further infos

		// skip useless lines, last line
		if (arrayLine.length == 1)
			return;

		String movieTitle = arrayLine[0].trim();
		Date date = DataHandlerUtils.extractDate(arrayLine[1]);
		String country = DataHandlerUtils.extractCountry(arrayLine[1]);

		// if something else is wrong
		if (!DataHandlerUtils.isValidReleaseString(arrayLine[1])
				|| !DataHandlerUtils.isInTimeRange(date.toString())) {
			return;
		}

		// get the movie from the DB
		selectMovStmt.setString(1, movieTitle);
		ResultSet movRS = selectMovStmt.executeQuery();

		// is the movie already in the DB ?
		if (movRS.next()) {

			if (currentMovie.equals(movieTitle)) {

				// still the same movie
				if (!isUSA && country.equals("USA")) {

					isUSA = true;

					// update
					update(date, country, movieTitle);
				}
			} else {

				// found a new movie
				currentMovie = movieTitle;

				// reset
				isUSA = false;

				// found a new movie from USA ?
				if (country.equals("USA")) {
					isUSA = true;
				}

				// update
				update(date, country, movieTitle);
			}
		}
	}

	private void update(java.sql.Date date, String country, String movieTitle)
			throws SQLException {
		updateMovStmt.setDate(1, date);
		updateMovStmt.setString(2, country);
		updateMovStmt.setString(3, movieTitle);
		updateMovStmt.executeUpdate();
	}

	@Override
	protected void closeStatements() throws SQLException {
		updateMovStmt.close();
		selectMovStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {

		updateMovStmt = con.connection
				.prepareStatement("UPDATE Movie SET release = ?, rel_country = ? WHERE title = ?;");

		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");
	}
}
