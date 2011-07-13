package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class ActorsActressDataHandler extends AbstractDataHandler {

	private PreparedStatement selectMovStmt = null;
	private PreparedStatement selectActStmt = null;
	private PreparedStatement insertActStmt = null;
	private PreparedStatement insertStarringInStmt = null;
	
	private String gender;

	static private DBConnector con;
	
	public ActorsActressDataHandler(String filename, int skipLineNumber, int endLineNumber, String pattern, String gender) {
		super(filename, skipLineNumber , endLineNumber, pattern);
		this.gender = gender;
		con = DBConnector.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertActStmt.close();
		insertStarringInStmt.close();
		selectMovStmt.close();
		selectActStmt.close();
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

		// get the movie from the DB
		selectMovStmt.setString(1, title);
		ResultSet movRS = selectMovStmt.executeQuery();

		// is the movie in the DB?
		if (movRS.next()) {

			// only if the actor is not added yet
			if (actorIsInDB == false) {
				
				// check weather it was added before some time
				selectActStmt.setString(1, currentActor);
				ResultSet actRS = selectActStmt.executeQuery();
				
				if (!actRS.next()){
					
					// add actor to DB
					insertActStmt.setString(1, currentActor);
					insertActStmt.setString(2, gender);
					insertActStmt.execute();
					actorIsInDB = true;
				}
			}

			//  add the tupel to starring
			insertStarringInStmt.setString(1, title);
			insertStarringInStmt.setString(2, currentActor);
			insertStarringInStmt.execute();
		}
	}

	@Override
	protected void prepareStatements() throws SQLException {
		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");

		selectActStmt = con.connection
				.prepareStatement("SELECT * FROM Actor WHERE name = ?;");

		insertActStmt = con.connection
				.prepareStatement("INSERT INTO Actor (name, gender) VALUES (?,?)");

		insertStarringInStmt = con.connection
				.prepareStatement("INSERT INTO Starring (movie, actor) VALUES (?,?)");
	}
}
