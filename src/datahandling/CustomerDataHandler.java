package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class CustomerDataHandler extends AbstractDataHandler {

	private PreparedStatement insertStmt;
	private DBConnector con;
	private Cache cache;

	public CustomerDataHandler() {

		super("Daten/customers.list", 1, 1001, ",");

		// get database connection
		this.con = DBConnector.getInstance();
		cache = Cache.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		insertStmt.setInt(1, Integer.parseInt(arrayLine[0]));
		insertStmt.setString(2, arrayLine[1]);
		insertStmt.setString(3, arrayLine[2]);

		// split the street and the number
		int number = Integer.parseInt(arrayLine[3].substring(
				arrayLine[3].lastIndexOf(" ")).trim());
		String street = arrayLine[3]
				.substring(0, arrayLine[3].lastIndexOf(" "));

		insertStmt.setString(4, street);
		insertStmt.setInt(5, number);
		insertStmt.setInt(6, Integer.parseInt(arrayLine[4]));
		insertStmt.setString(7, arrayLine[5]);
		insertStmt.setString(8, arrayLine[6]);
		insertStmt.addBatch();
		cache.customer.add(Integer.parseInt(arrayLine[0]));
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {

		insertStmt = con.connection
				.prepareStatement("insert into customer"
						+ "(id, surname, forename, street, streetnumber, zip, city, telephone)"
						+ "VALUES (?,?,?,?,?,?,?,?);");
	}

	@Override
	protected void executeBatches() throws SQLException {
		insertStmt.executeBatch();
		con.connection.commit();
	}
}
