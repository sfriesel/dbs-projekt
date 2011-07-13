package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class RentalsDataHandler extends AbstractDataHandler {
	
	static private DBConnector con;
	
	public RentalsDataHandler() {
		super("Daten/rentals.list", 1, 0, "\t+");

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
