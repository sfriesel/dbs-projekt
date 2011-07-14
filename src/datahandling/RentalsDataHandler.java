package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.DBConnector;

public class RentalsDataHandler extends AbstractDataHandler {

	private PreparedStatement insertRentalStmt = null;

	private DBConnector con;
	private Cache cache;

	public RentalsDataHandler() {
		super("Daten/rentals.list", 1, 1149, "\t+");

		// get database connection
		con = DBConnector.getInstance();
		cache = Cache.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		int id = Integer.parseInt(arrayLine[0]);
		String type = arrayLine[1];
		String title = arrayLine[2];
		Timestamp start = DataHandlerUtils.stringToTimestamp(arrayLine[3]);
		int duration = Integer.parseInt(arrayLine[4]);

		// only if the movie is in our DB add rental to DB
		if (cache.movie.contains(title) && (cache.customer.contains(id))) {

			insertRentalStmt.setInt(1, id);
			insertRentalStmt.setString(2, type);
			insertRentalStmt.setString(3, title);
			insertRentalStmt.setTimestamp(4, start);
			insertRentalStmt.setInt(5, duration);
			insertRentalStmt.addBatch();
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
	}

	@Override
	protected void executeBatches() throws SQLException {
		insertRentalStmt.executeBatch();
		con.connection.commit();
	}
}
