package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

import database.DBConnector;

public class ActorsActressDataHandler extends AbstractDataHandler {

	private PreparedStatement insertActStmt = null;
	private PreparedStatement insertStarringInStmt = null;

	private String gender;

	private DBConnector con;
	private HashSet<String> actorCache;
	private Cache cache;

	public ActorsActressDataHandler(String filename, int skipLineNumber,
			int endLineNumber, String pattern, String gender) {
		super(filename, skipLineNumber, endLineNumber, pattern);
		this.gender = gender;
		con = DBConnector.getInstance();
		cache = Cache.getInstance();
		actorCache = new HashSet<String>();
	}
  
	@Override
	protected void closeStatements() throws SQLException {
		actorCache = null;
		insertActStmt.close();
		insertStarringInStmt.close();
	}

	private String currentActor = null;
	private boolean actorIsInDB = false;

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// new actor starts in the following line, reset everything
		if (arrayLine.length == 1) {
			currentActor = null;
			actorIsInDB = false;
			return;
		}
		
		String actor = arrayLine[0];
		// title consists of the title and also further infos about the actor
		String title = arrayLine[1].split("  ")[0];

		// Actor is not empty
		if (!actor.equals("")) {
			currentActor = actor;
		}
		
		// ignore movies, which do not contain 2010/2011
		if (!DataHandlerUtils.isInTimeRange(title))
			return;

		// is the movie in the cache?
		if (cache.movie.contains(title)) {

			// only if the actor is not added yet
			if (actorIsInDB == false) {

				// check weather it was added before some time
				if (!actorCache.contains(currentActor + "\t" + gender)) {

					// add actor to DB
					insertActStmt.setString(1, currentActor);
					insertActStmt.setString(2, gender);
					insertActStmt.addBatch();

					actorCache.add(currentActor + "\t" + gender);
					actorIsInDB = true;
				}
			}

			insertStarringInStmt.setString(1, title);
			insertStarringInStmt.setString(2, currentActor);
			insertStarringInStmt.setString(3, gender);
			insertStarringInStmt.addBatch();
		}
	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertActStmt = con.connection
				.prepareStatement("INSERT INTO Actor (name, gender) VALUES (?,?)");
		insertStarringInStmt = con.connection
				.prepareStatement("INSERT INTO Starring (movie, actor, gender) VALUES (?,?,?)");
	}

	@Override
	protected void executeBatches() throws SQLException {
		insertActStmt.executeBatch();
		insertStarringInStmt.executeBatch();
	}
}
