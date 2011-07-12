package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class ActressDataHandler extends AbstractDataHandler {

	static private DBConnector con;

	public ActressDataHandler() {
		super("Daten/actresses.list", 1, 0, "\t+");

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
