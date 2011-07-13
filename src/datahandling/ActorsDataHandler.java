package datahandling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class ActorsDataHandler extends AbstractDataHandler {

	private PreparedStatement selectMovStmt = null;
	private PreparedStatement selectActorStmt = null;
	private PreparedStatement insertActStmt = null;
	private PreparedStatement insertStarringInStmt = null;

	static private DBConnector con;

	public ActorsDataHandler() {
		super("Daten/actors.list", 239, 11272388, "\t+");

		con = DBConnector.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertActStmt.close();
		insertStarringInStmt.close();
		selectMovStmt.close();
	}

	private String currentActor = null;
	private boolean actorIsInDB = false;

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// ignore lines with more than 2 entries
		if (arrayLine.length >= 3) {
			return;
		}

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

			if (actorIsInDB == false) {
				
				// falls er noch nicht in der DB ist füge ihn hinzu
				selectActorStmt.setString(1, currentActor);
				ResultSet actRS = selectActorStmt.executeQuery();
				
				if (currentActor.equals("") || currentActor == null){
					System.out.println(arrayLine[0] + "***" + arrayLine[1]);
					return;
				}
				
				if (!actRS.next()){
					
					// add actor to DB
					insertActStmt.setString(1, currentActor);
					insertActStmt.setString(2, "m");
					insertActStmt.execute();
					actorIsInDB = true;
				}
				else {
					System.out.println(currentActor);
				}
			}

			insertStarringInStmt.setString(1, title);
			insertStarringInStmt.setString(2, currentActor);
		}
	}

	@Override
	protected void prepareStatements() throws SQLException {
		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");

		selectActorStmt = con.connection
				.prepareStatement("SELECT * FROM Actor WHERE name = ?;");

		insertActStmt = con.connection
				.prepareStatement("INSERT INTO Actor (name, gender) VALUES (?,?)");

		insertStarringInStmt = con.connection
				.prepareStatement("INSERT INTO Starring (movie, actor) VALUES (?,?)");
	}
}
