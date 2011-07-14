package datahandling;

import java.sql.SQLException;

import database.DBConnector;

public class PlotDataHandler extends AbstractDataHandler {
	
	private DBConnector con;

	public PlotDataHandler() {
		super("Daten/plot.list", 15, 3469255, "\t+");
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

	@Override
	protected void executeBatches() throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
