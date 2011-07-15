package datahandling;

import java.sql.SQLException;

public class PlotDataHandler extends AbstractDataHandler {

	public PlotDataHandler() {
		super("Daten/plot.list", 15, 3469255, "\t+");
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
