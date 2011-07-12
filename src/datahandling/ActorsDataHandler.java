package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class ActorsDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/actors.list";
	static final private int lineNumber = 239;
	static final private String pattern = "\t+";

	private PreparedStatement selectMovStmt = null;
	private PreparedStatement insertActStmt = null;
	private PreparedStatement insertStarringInStmt = null;

	static private DBConnector con;

	public ActorsDataHandler() {
		super(filename, lineNumber, pattern);

		con = DBConnector.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {
		// Tabelle Movie, Arctor/Actress, und StarringIn
		// Vorgehensweise:
		// Actor merken, alle Filme parsen
		// schauen ob es den Film überhaupt gibt
		// nur wenn es den Film gibt, den Actor hinzufügen
		// wenn alle Filme von ihm/ihr unbekannt actor wegschmeißen
		// d.h. mindestens ein Film muss in der DB sein, damit wir den
		// abspeichern
		// dann auch starring in speichern
		// evtl. schauen ob actors doppelt sind
	}

	@Override
	protected void prepareStatements() throws SQLException {
		selectMovStmt = con.connection
				.prepareStatement("SELECT * FROM Movie WHERE title = ?;");

		insertActStmt = con.connection
				.prepareStatement("INSERT INTO Actor (forename, lastname, gender) VALUES (?,?,?)");

		insertStarringInStmt = con.connection
				.prepareStatement("INSERT INTO Starring (movie, actor) VALUES (?,?)");

	}

}