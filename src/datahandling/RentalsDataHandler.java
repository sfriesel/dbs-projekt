package datahandling;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.DBConnector;

public class RentalsDataHandler extends AbstractDataHandler {

	private PreparedStatement selectMovStmt = null;
	private PreparedStatement insertRentalStmt = null;

	static private DBConnector con;

	public RentalsDataHandler() {
		super("Daten/rentals.list", 1, 1149, "\t+");

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		int id = Integer.parseInt(arrayLine[0]);
		String type = arrayLine[1];
		String title = arrayLine[2];
		Timestamp start = DataHandlerUtils.stringToTimestamp(arrayLine[3]);
		int duration = Integer.parseInt(arrayLine[4]);
		
		selectMovStmt.setString(1, title);
		ResultSet movRS = selectMovStmt.executeQuery();
		
		// only if the movie is in our DB add retal to DB
		if (movRS.next()){
			insertRentalStmt.setInt(1, id);
			insertRentalStmt.setString(2, type);
			insertRentalStmt.setString(3, title);
			insertRentalStmt.setTimestamp(4, start);
			insertRentalStmt.setInt(5, duration);
			insertRentalStmt.execute();
		}
	
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertRentalStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertRentalStmt = con.connection
				.prepareStatement("INSERT INTO Rental (customer, priceModel, movie, start, duration) VALUES (?,?,?,?,?);");
	
		selectMovStmt = con.connection.prepareStatement("SELECT * FROM Movie WHERE title = ?;");
	}
}
