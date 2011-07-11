package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class ActressDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/actresses.list";
	static final private int lineNumber = 1;
	static final private String pattern = "\t+";

	static private DBConnector con;

	public ActressDataHandler() {
		super(filename, lineNumber, pattern);

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
