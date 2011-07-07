package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class CustomerDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/customers.list";
	static final private int lineNumber = 1;
	static final private String pattern = ",";

	private PreparedStatement pstmt = null;

	public CustomerDataHandler() {

		super(filename, lineNumber, pattern);

		// get database connection
		DBConnector con = DBConnector.getInstance();

		// prepare the statement
		try {
			pstmt = con.connection
					.prepareStatement("insert into customer"
							+ "(customer_id, surename, forename, street, number, zip, city, telephone)"
							+ "VALUES (?,?,?,?,?,?,?,?);");
		} catch (SQLException e) {
			System.out.println("Error while creating a prepared statement.");
			e.printStackTrace();
		}
	}

	@Override
	protected void insertDB(String[] arrayLine) {

		try {
			pstmt.setInt(1, Integer.parseInt(arrayLine[0]));
			pstmt.setString(2, arrayLine[1]);
			pstmt.setString(3, arrayLine[2]);
			
			// split the street and the  number
			int number = Integer.parseInt(arrayLine[3].substring(arrayLine[3].lastIndexOf(" ")).trim());
			String street = arrayLine[3].substring(0, arrayLine[3].lastIndexOf(" "));
			
			pstmt.setString(4, street);
			pstmt.setInt(5, number);
			pstmt.setInt(6, Integer.parseInt(arrayLine[4]));
			pstmt.setString(7, arrayLine[5]);
			pstmt.setString(8, arrayLine[6]);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Inserting new Customers did not work.");
			e.printStackTrace();
		}
	}
}
