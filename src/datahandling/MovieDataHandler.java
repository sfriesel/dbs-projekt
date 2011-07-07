package datahandling;

import database.DBConnector;

public class MovieDataHandler extends AbstractDataHandler {

	public MovieDataHandler(String filename, int lineNumber, String pattern) {
		super(filename, lineNumber, pattern);
		
		// get database connection
		DBConnector con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) {
		// TODO Auto-generated method stub

	}

}
