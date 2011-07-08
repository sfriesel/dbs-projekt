package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class ReleaseDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/release-dates.list";
	static final private int lineNumber = 14;
	static final private String pattern = "\t+";

	private PreparedStatement pstmt = null;

	public ReleaseDataHandler() {
		super(filename, lineNumber, pattern);

		// get database connection
		DBConnector con = DBConnector.getInstance();

		// prepare the statement
		try {
			pstmt = con.connection
					.prepareStatement("UPDATE Movie SET release = ?, release_reg = ? WHERE title = ?;");
		} catch (SQLException e) {
			System.out.println("Error while creating a prepared statement.");
			e.printStackTrace();
		}
		// "UPDATE movies SET release_Date = ?, region = ? WHERE title = ?"
	}

	@Override
	protected void insertDB(String[] arrayLine) {

		// arrayLine[0] -> title
		// arrayLine[1] -> release
		// arrayLine[2] -> further infos

		// skip useless lines, last line
		if (arrayLine.length == 1)
			return;

		// is the movie already in the DB ?
		if (DataHandlerUtils.isAlreadyInserted(arrayLine[0], "movie", "title")) {

			// release date in the USA
			if (arrayLine[1].contains("USA")) {

				String date = DataHandlerUtils.extractDate(arrayLine[1]);

				// add to DB
				try {
					// TODO: setDate verwenden
					pstmt.setString(1, date);
					// TODO: deal with the release country USA and noUSA
					pstmt.setString(2, "USA");
					pstmt.setString(3, arrayLine[0]);
				} catch (SQLException e) {
					System.out
							.println("Inserting the reasese date did not work.");
					e.printStackTrace();
				}
			}
		}
	}
}
