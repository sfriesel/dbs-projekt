package testing;

import java.io.IOException;
import java.sql.SQLException;
import database.DBConnector;

public class TestDataHandlers {
	public static void main(String[] args) {
		
		DBConnector.configure("localhost", "5432", "movies", "csw", "csw");
		DBConnector con = DBConnector.getInstance();
		try {
			con.resetDB();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}
