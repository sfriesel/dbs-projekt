package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class CustomerDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/customers.list";
	static final private int lineNumber = 1;
	static final private String pattern = ",";

	private PreparedStatement insertStmt; final DBConnector con;

	public CustomerDataHandler() {

		super(filename, lineNumber, pattern);

		// get database connection
		this.con = DBConnector.getInstance();
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
		insertStmt.execute();
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {

		insertStmt = con.connection
				.prepareStatement("insert into customer"
						+ "(customer_id, surename, forename, street, number, zip, city, telephone)"
						+ "VALUES (?,?,?,?,?,?,?,?);");
	}
}
