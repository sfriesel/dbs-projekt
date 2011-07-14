package datahandling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnector;

public class MovieDataHandler extends AbstractDataHandler {

	private PreparedStatement insertMovStmt = null;

	private DBConnector con;
	private Cache cache;

	public MovieDataHandler() {
		super("Daten/modmovies.list", 4, 124769, "\t+");

		// get database connection
		con = DBConnector.getInstance();
		cache = Cache.getInstance();
	}

	@Override
	protected void insertDB(String[] arrayLine) throws SQLException {

		// arrayLine[0] -> title
		// arrayLine[1] -> year(s)
		// arrayLine[2] -> category

		// only parse lines, which are in the correct form
		if (arrayLine.length == 3) {

			// ignore movies, which do not contain 2010/2011
			if (!DataHandlerUtils.isInTimeRange(arrayLine[0]))
				return;

			insertMovStmt.setString(1, arrayLine[0]);
			insertMovStmt.setString(2, arrayLine[2]);
			insertMovStmt.addBatch();
			cache.movie.add(arrayLine[0]);
		}
	}

	@Override
	protected void closeStatements() throws SQLException {
		insertMovStmt.close();
	}

	@Override
	protected void prepareStatements() throws SQLException {
		insertMovStmt = con.connection.prepareStatement("insert into movie "
				+ "(title, category) VALUES (?,?);");
	}

	@Override
	protected void executeBatches() throws SQLException {
		insertMovStmt.executeBatch();
	}
}
