package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import database.DBConnector;

public class ReleaseDataHandler extends AbstractDataHandler {

	private PreparedStatement updateMovStmt;

	private final DBConnector con;
	private final Cache cache;

	private String currentMovie = "";
	private boolean isUSA = false;

	public ReleaseDataHandler() {
		super("Daten/release-dates.list", 14, 2304630, "\t+");

		// get database connection
		con = DBConnector.getInstance();
		cache = Cache.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// arrayLine[0] -> title
		// arrayLine[1] -> release+country
		// arrayLine[2] -> further infos

		// check for valid release string (country:date)
		// and year must be >= 2010
		if (!DataHandlerUtils.isValidReleaseString(arrayLine[1])
				|| !DataHandlerUtils.isInTimeRange(arrayLine[1])) {
			return;
		}

		String movieTitle = arrayLine[0].trim();
		Date date = DataHandlerUtils.extractDate(arrayLine[1]);
		String country = DataHandlerUtils.extractCountry(arrayLine[1].trim());

		// get the movie from the DB
		if (cache.movie.contains(movieTitle)) {

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
		updateMovStmt.addBatch();
	}

	@Override
	protected void closeStatements() throws SQLException {
		updateMovStmt.executeBatch();
		con.connection.commit();
		updateMovStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {

		updateMovStmt = con.connection
				.prepareStatement("UPDATE Movie SET release = ?, rel_country = ? WHERE title = ?;");
	}
}
