package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class DirectorsDataHandler extends AbstractDataHandler {

	private PreparedStatement selectMovStmt = null;
	private PreparedStatement selectDirStmt = null;
	private PreparedStatement selectDirectedByStmt = null;
	private PreparedStatement insertDirStmt = null;
	private PreparedStatement insertDirectedByStmt = null;

	static private DBConnector con;

	public DirectorsDataHandler() {
		super("Daten/directors.list", 235, 1549103, "\t+");

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
		selectMovStmt.close();
		insertDirStmt.close();
		insertDirectedByStmt.close();
	}

	private String currentDirector = null;
	private boolean directorIsInDB = false;

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// new director starts in the following line, reset everything
		if (arrayLine.length == 1) {
			currentDirector = null;
			directorIsInDB = false;
			return;
		}

		String director = arrayLine[0];
		// title consists of the title and also further infos about the actor
		String title = arrayLine[1].split("  ")[0];

		// Director is not empty
		if (!director.equals("")) {
			currentDirector = director;
		}

		// get the movie from the DB
		selectMovStmt.setString(1, title);
		ResultSet movRS = selectMovStmt.executeQuery();

		// is the movie in the DB?
		if (movRS.next()) {

			// only if the director is not added yet
			if (directorIsInDB == false) {

				// check weather it was added before some time
				selectDirStmt.setString(1, currentDirector);
				ResultSet dirRS = selectDirStmt.executeQuery();

				if (!dirRS.next()) {

					// add director to DB
					insertDirStmt.setString(1, currentDirector);
					insertDirStmt.execute();
					directorIsInDB = true;
				}
			}

			// is the tupel (movie,director) already in the DB?
			selectDirectedByStmt.setString(1, title);
			selectDirectedByStmt.setString(2, currentDirector);
			ResultSet dirByRS = selectDirectedByStmt.executeQuery();

			// is its not in the DB add it
			if (!dirByRS.next()) {
				insertDirectedByStmt.setString(1, title);
				insertDirectedByStmt.setString(2, currentDirector);
				insertDirectedByStmt.execute();
			}
		}
	}

	@Override
	protected void prepareStatements() throws SQLException {
		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");

		selectDirStmt = con.connection
				.prepareStatement("SELECT * FROM Director WHERE name = ?;");

		selectDirectedByStmt = con.connection
				.prepareStatement("SELECT * FROM DirectedBy WHERE movie = ? AND director = ?;");

		insertDirStmt = con.connection
				.prepareStatement("INSERT INTO Director (name) VALUES (?)");

		insertDirectedByStmt = con.connection
				.prepareStatement("INSERT INTO DirectedBy (movie, director) VALUES (?,?)");
	}
}
