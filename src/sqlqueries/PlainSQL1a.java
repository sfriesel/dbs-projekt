package sqlqueries;

import java.sql.*;
import database.*;

public class PlainSQL1a {
	public static void execute() throws SQLException {
		DBConnector con = DBConnector.getInstance();
		Statement stmt = con.connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT pricemodel, count(customer) "
			+ "FROM rental "
			+ "GROUP BY pricemodel");
		
		while(rs.next()) {
			System.out.println(rs.getString(1) + " " + rs.getInt(2));
		}
	}
}
