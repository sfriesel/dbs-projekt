package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class DirectorsDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/directors.list";
	static final private int lineNumber = 264;
	static final private String pattern = "\t+";
	
	static private DBConnector con;

	public DirectorsDataHandler() {
		super(filename, lineNumber, pattern);

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
