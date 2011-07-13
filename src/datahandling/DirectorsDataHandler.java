package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class DirectorsDataHandler extends AbstractDataHandler {
	
	private PreparedStatement selectMovStmt = null;
	private PreparedStatement selectDirStmt = null;
	private PreparedStatement insertDirStmt = null;
	private PreparedStatement insertStarringInStmt = null;
	
	static private DBConnector con;

	public DirectorsDataHandler() {
		super("Daten/directors.list", 235, 1549103, "\t+");

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {
		if (!arrayLine[0].equals("")){
			if (arrayLine[0].split(",").length == 1){
				System.out.println(arrayLine[0]);
			}
		}
	}

	@Override
	protected void prepareStatements() throws SQLException {
		// TODO Auto-generated method stub

	}
}
