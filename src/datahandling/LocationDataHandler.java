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
					+ "(country, location)" + "VALUES (?,?);");
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
		
		// only if the movie is released in 2010 or 2011, add it to the database
		if (DataHandlerUtils.isInTimeRange(arrayLine[0])){
			
			// arrayLine[1] -> full location
			// arrrayLine[1].last -> Country

			String country = null;
			String location = null;

			country = arrayLine[1].substring(arrayLine[1].lastIndexOf(",") + 1)
					.trim();
			location = arrayLine[1];

			try {
				pstmt.setString(1, country);
				pstmt.setString(2, location);
				pstmt.execute();
				
				// TODO: doppeltes einfügen von Werten verhindern? Wie?
			} catch (SQLException e) {
				System.out.println("Inserting new Location did not work.");
				e.printStackTrace();
			}
		
			// TODO: Also fill the table with movie and locations relation.
			// insert into shotin: location_id and movie_title
		}
	}
}
