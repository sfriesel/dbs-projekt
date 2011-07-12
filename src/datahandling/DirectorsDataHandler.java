package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class DirectorsDataHandler extends AbstractDataHandler {
	
	static private DBConnector con;

	public DirectorsDataHandler() {
		super("Daten/directors.list", 264, 0, "\t+");

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void closeStatements() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prepareStatements() throws SQLException {
		// TODO Auto-generated method stub

	}

}
