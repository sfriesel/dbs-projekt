package datahandling;

import database.DBConnector;

public class RentalsDataHandler extends AbstractDataHandler {
	
	static final private String filename = "Daten/rentals.list";
	static final private int lineNumber = 1;
	static final private String pattern = "\t+";
	
	public RentalsDataHandler() {
		super(filename, lineNumber, pattern);

		// get database connection
		DBConnector con = DBConnector.getInstance();
	}

	

	@Override
	protected void insertDB(String[] arrayLine) {
		// TODO Auto-generated method stub

	}

}
