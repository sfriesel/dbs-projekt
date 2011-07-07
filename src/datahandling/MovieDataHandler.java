package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class MovieDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/modmovies.list";
	static final private int lineNumber = 4;
	static final private String pattern = "\t+";

	private PreparedStatement pstmt = null;
	
	int wr = 0;

	public MovieDataHandler() {
		super(filename, lineNumber, pattern);

		// get database connection
		DBConnector con = DBConnector.getInstance();

		// prepare the statement
		try {
			pstmt = con.connection.prepareStatement("insert into movie"
					+ "(title, category)" + "VALUES (?,?);");
		} catch (SQLException e) {
			System.out.println("Error while creating a prepared statement.");
			e.printStackTrace();
		}
	}

	@Override
	protected void insertDB(String[] arrayLine) {
		
		// arrayLine[0]	-> title
		// arrayLine[1]	-> year(s)
		// arrayLine[2]	-> category

		// only parse lines, which are in the correct form
		// and have the year 2010 or 2011, ???? are ignored
		if (arrayLine.length == 3 && (arrayLine[1].contains("2010") || arrayLine[1].contains("2011"))) {

			try {
				pstmt.setString(1, arrayLine[0]);
				pstmt.setString(2, arrayLine[2]);
				pstmt.execute();
			} catch (SQLException e) {
				System.out.println("Inserting new Movie did not work.");
				e.printStackTrace();
			}
		}
	}
}
