package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class MovieDataHandler extends AbstractDataHandler {

	static final private String filename = "Daten/modmovies.list";
	static final private int lineNumber = 4;
	static final private String pattern = "\t+";

	private PreparedStatement insertMovStmt = null;

	private final DBConnector con;

	public MovieDataHandler() {
		super(filename, lineNumber, pattern);

		// get database connection
		con = DBConnector.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// arrayLine[0] -> title
		// arrayLine[1] -> year(s)
		// arrayLine[2] -> category

		// only parse lines, which are in the correct form
		// and have the year 2010 or 2011, ???? are ignored
		if (arrayLine.length == 3
				&& DataHandlerUtils.isInTimeRange(arrayLine[1])) {

			insertMovStmt.setString(1, arrayLine[0]);
			insertMovStmt.setString(2, arrayLine[2]);
			insertMovStmt.execute();
		}
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertMovStmt.close();

	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertMovStmt = con.connection.prepareStatement("insert into movie"
				+ "(title, category)" + "VALUES (?,?);");
	}
}
