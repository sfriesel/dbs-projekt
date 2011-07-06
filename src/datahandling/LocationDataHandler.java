package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class LocationDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/locations.list";
	static final private int lineNumber = 264;
	static final private String pattern = "\t+";

	private PreparedStatement pstmt = null;

	public LocationDataHandler() {

		super(filename, lineNumber, pattern);

		// get database connection
		DBConnector con = DBConnector.getInstance();

		// prepare the statement
		try {
			pstmt = con.connection.prepareStatement("insert into locations"
					+ "(movie_title, country, location)" + "VALUES (?,?,?);");
		} catch (SQLException e) {
			System.out.println("Error while creating a prepared statement.");
			e.printStackTrace();
		}

	}

	@Override
	protected void insertDB(String[] arrayLine) {

		// ignore empty lines in the end
		if (arrayLine.length == 1) {
			return;
		}

		// arrayLine[0] -> Title
		// arrayLine[1] -> Location
		// arrrayLine[1].last -> Country
		// bei Locations gibt es bis zu 9 Werten

		String country = null;
		String location = null;

		if (arrayLine[1].lastIndexOf(",") != -1) {
			country = arrayLine[1].substring(arrayLine[1].lastIndexOf(",") + 1)
					.trim();
			location = arrayLine[1].substring(0, arrayLine[1].lastIndexOf(","));
		} else {
			country = arrayLine[1];
		}

		try {
			pstmt.setString(1, arrayLine[0]);
			pstmt.setString(2, country);
			pstmt.setString(3, location);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Inserting new Location did not work.");
			e.printStackTrace();
		}

	}

}
