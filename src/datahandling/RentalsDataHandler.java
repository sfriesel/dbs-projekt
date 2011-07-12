package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class RentalsDataHandler extends AbstractDataHandler {
	
	static final private String filename = "Daten/rentals.list";
	static final private int lineNumber = 1;
	static final private int endLineNumber = 0;
	static final private String pattern = "\t+";
	
	static private DBConnector con;
	
	public RentalsDataHandler() {
		super(filename, lineNumber, endLineNumber, pattern);

		// get database connection
		con = DBConnector.getInstance();
	}

	

	@Override
	protected void insertDB(String[] arrayLine) {
		// TODO Auto-generated method stub

	}



	@Override
	protected void closeStatements() throws SQLException {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void prepareStatements() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
