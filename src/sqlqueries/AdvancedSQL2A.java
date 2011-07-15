package sqlqueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Statement;

import view.PrintResult;

import database.DBConnector;

/**
 * Bestimmen Sie alle Kunden mit Preismodell Flat, welche basierend auf ihren
 * bisherigen Ausleihvorgängen im Modell Starter billiger weggekommen wären.
 * 
 * @author alexa
 * 
 */
public class AdvancedSQL2A {

	static PreparedStatement getRentalWithIDStmt = null;
	static PreparedStatement getCustomerWithFlatStmt = null;

	private static DBConnector con;

	public static void execute() throws SQLException {
		
		PrintResult pResult = new PrintResult();
		pResult.setDescription("Alle Kunden die mit dem Preismodell STARTER besser gekommen wären.");
		String[] head = {"CustomerID", "Price-FLAT", "Price-STARTER"};
		pResult.setHead(head);

		// get all Customer IDs which used a flat
		ResultSet idRS = getCustomerWithFlatStmt.executeQuery();

		// as long as there are new customers
		while (idRS.next()) {

			int currentMonth = -1;
			int currentYear = 0;
			boolean hadFirstBMovie = false;
			float sumFLAT = 0;
			float sumSTARTER = 0;

			// get all rentals from that customer
			int id = idRS.getInt("customer");
			ResultSet rentalRS = getRentalsWithID(id);

			// as long as there are new rentals
			while (rentalRS.next()) {

				Timestamp start = rentalRS.getTimestamp("start");
				int duration = rentalRS.getInt("duration");
				String type = rentalRS.getString("category");

				// is it a new Month?
				if ((start.getMonth() != currentMonth)
						|| (start.getMonth() == currentMonth && start.getYear() != currentYear)) {

					// change month and add 10€
					currentMonth = start.getMonth();
					currentYear = start.getYear();
					sumFLAT += 10;
				}

				// if it was a movie of type A
				if (type.equals("A")) {
					sumFLAT += 0.19 * duration;
					sumSTARTER += 1.29 * duration;
				}

				// if it was a movie of type B
				else {

					// if first free is over
					if (hadFirstBMovie) {
						sumFLAT += 0.19 * duration;
					}

					// oh its the first B movie ever
					else {
						hadFirstBMovie = true;
					}

					sumSTARTER += 0.79 * duration;
				}
			}

			// falls starter < flat
			// add to result
			if (sumSTARTER < sumFLAT){
				String[] row = {String.valueOf(id), String.valueOf(sumFLAT), String.valueOf(sumSTARTER)}; 
				pResult.addRow(row);
			}	
		}
		pResult.print();
	}

	private static void prepare() throws SQLException {

		// DBConnection
		DBConnector.configure("localhost", "5432", "movies", "alexa", "dinkel");
		con = DBConnector.getInstance();

		// prepare Statements
		getRentalWithIDStmt = con.connection
				.prepareStatement("SELECT Rental.start, Rental.duration, Movie.category FROM Rental, "
						+ "Movie WHERE Movie.title = Rental.movie AND customer = ? ORDER BY start;");
		getCustomerWithFlatStmt = con.connection
				.prepareStatement("SELECT DISTINCT customer FROM Rental WHERE pricemodel = 'flat';");
	}

	private static ResultSet getRentalsWithID(int id) throws SQLException {
		getRentalWithIDStmt.setInt(1, id);
		return getRentalWithIDStmt.executeQuery();
	}

	public static void main(String[] args) {
		try {
			prepare();
			execute();
		} catch (SQLException e) {
			e.printStackTrace();
			e.getNextException().printStackTrace();
		}
	}
}