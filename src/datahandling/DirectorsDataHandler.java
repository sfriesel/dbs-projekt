package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class DirectorsDataHandler extends AbstractDataHandler {

	private PreparedStatement insertDirStmt = null;
	private PreparedStatement insertDirectedByStmt = null;

	private DBConnector con;
	private Cache cache = null;

	public DirectorsDataHandler() {
		super("Daten/directors.list", 235, 1549103, "\t+");

		// get database connection
		con = DBConnector.getInstance();
		cache = Cache.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
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
		if (cache.movie.contains(title)) {

			// only if the director is not added yet
			if (directorIsInDB == false) {

				// check weather it was added before some time

				if (!cache.director.contains(currentDirector)) {

					// add director to DB
					insertDirStmt.setString(1, currentDirector);
					insertDirStmt.addBatch();
					directorIsInDB = true;
					cache.director.add(currentDirector);
				}
			}

			// is the tuple (movie,director) already in the DB?
			if (!cache.directedBy.contains(title + "\t" + currentDirector)) {

				insertDirectedByStmt.setString(1, title);
				insertDirectedByStmt.setString(2, currentDirector);
				insertDirectedByStmt.addBatch();
				cache.directedBy.add(title + "\t" + currentDirector);
			}
		}
	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertDirStmt = con.connection
				.prepareStatement("INSERT INTO Director (name) VALUES (?)");
		insertDirectedByStmt = con.connection
				.prepareStatement("INSERT INTO DirectedBy (movie, director) VALUES (?,?)");
	}

	@Override
	protected void executeBatches() throws SQLException {
		insertDirStmt.executeBatch();
		insertDirectedByStmt.executeBatch();
	}
}
