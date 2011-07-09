package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class ReleaseDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/release-dates.list";
	static final private int lineNumber = 14;
	static final private String pattern = "\t+";

	private PreparedStatement insertStmt;
	private PreparedStatement selectMovStmt;

	private final DBConnector con;

	private String currentMovie = null;
	private String releaseDate = null;
	private boolean isUSA = false;

	public ReleaseDataHandler() {
		super(filename, lineNumber, pattern);

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// arrayLine[0] -> title
		// arrayLine[1] -> release+country
		// arrayLine[2] -> further infos

		String movieTitle = arrayLine[0];
		String releaseString = arrayLine[1];

		// skip useless lines, last line
		if (arrayLine.length == 1)
			return;

		// get the movie from the DB
		selectMovStmt.setString(1, movieTitle);
		ResultSet movRS = selectMovStmt.executeQuery();

		// is the movie already in the DB ?
		if (movRS.next()) {

			String country = releaseString.split(":")[1];

			// is it still the same movie ?
			if (currentMovie == movieTitle) {

				// is USA not found yet ?
				if (!isUSA && (country.equals("USA"))) {
					isUSA = true;
					releaseDate = DataHandlerUtils.extractDate(releaseString);
				}

			} else {
				// movie title has changed
				currentMovie = movieTitle;

				// is the first date from the USA?
				if (country.equals("USA")) {
					isUSA = true;
				}

				// save the release date
				releaseDate = DataHandlerUtils.extractDate(releaseString);
			}
		}
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertStmt.close();
		selectMovStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {

		insertStmt = con.connection
				.prepareStatement("UPDATE Movie SET release = ?, rel_country = ? WHERE title = ?;");

		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");
	}
}
