package sqlqueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Statement;

import database.DBConnector;

/**
 * Bestimmen Sie alle Kunden mit Preismodell Flat, welche basierend auf ihren
 * bisherigen Ausleihvorgängen im Modell Starter billiger weggekommen wären.
 * 
 * @author alexa
 * 
 */
public class AdvancedSQL2A {

	static PreparedStatement getMovieTypeStmt = null;
	static PreparedStatement getRentalWithIDStmt = null;
	static PreparedStatement getCustomerWithFlatStmt = null;

	// alle Kunden finden, welche Ausleihen hatten mit Modell Flat
	// gruppiere nach Kunden
	//
	// Modell FLAT:
	// 10€ pro monat
	// A: 0.19€ pro Tag
	// B: 0.19€ pro Tag (erster film ist kostenlos)
	// Modell STARTER
	// A:1.29€ pro Tag
	// B:0.79€ pro Tag

	private static DBConnector con;

	public static void execute() throws SQLException {

		// get all Customer IDs which used a flat
		ResultSet idRS = getCustomerWithFlatStmt.executeQuery();

		// as long as there are new customers
		while (idRS.next()) {

			int currentMonth = -1;
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
				String title = rentalRS.getString("movie");

				// is it a new Month?
				if (start.getMonth() != currentMonth) {

					// change month and add 10€
					currentMonth = start.getMonth();
					sumFLAT += 10;
				}

				// if it was a movie of type A
				if (getMovieType(title).equals("A")) {
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
			if (sumSTARTER < sumFLAT)
				System.out.println(id + "*" + sumSTARTER + "*" + sumFLAT);

			hadFirstBMovie = false;
			currentMonth = -1;
			sumFLAT = 0;
			sumSTARTER = 0;
		}
	}

	private static void prepareStatements() throws SQLException {

		// DBConnection
		DBConnector.configure("localhost", "5432", "movies", "alexa", "dinkel");
		con = DBConnector.getInstance();

		getMovieTypeStmt = con.connection
				.prepareStatement("SELECT category FROM Movie WHERE title = ?;");
		getRentalWithIDStmt = con.connection
				.prepareStatement("SELECT * FROM Rental WHERE customer = ? ORDER BY customer;");
		getCustomerWithFlatStmt = con.connection
				.prepareStatement("SELECT DISTINCT customer FROM Rental WHERE pricemodel = 'flat';");
	}

	private static String getMovieType(String title) throws SQLException {
		getMovieTypeStmt.setString(1, title);
		ResultSet rs = getMovieTypeStmt.executeQuery();
		rs.next();
		return rs.getString("category");
	}

	private static ResultSet getRentalsWithID(int id) throws SQLException {
		getRentalWithIDStmt.setInt(1, id);
		ResultSet rs = getRentalWithIDStmt.executeQuery();
		rs.next();
		return rs;
	}

	public static void main(String[] args) {
		try {
			prepareStatements();
			execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getNextException().printStackTrace();
		}
	}
}
