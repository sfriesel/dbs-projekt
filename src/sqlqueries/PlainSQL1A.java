package sqlqueries;

import java.sql.*;

import cli.PrintResult;

import cli.MenuEntryInterface;
import database.*;

public class PlainSQL1A implements MenuEntryInterface {

	@Override
	public String getName() {
		return "1A-Verteilung der Preismodelle";
	}

	@Override
	public String getDescription() {
		return "Wieviele Kunden haben sich für welches Preismodell entschieden?";
	}

	@Override
	public void execute() throws SQLException {
		DBConnector con = DBConnector.getInstance();
		
		Statement stmt = con.connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT pricemodel, count( DISTINCT customer) "
				+ "FROM rental " + "GROUP BY pricemodel;");

		PrintResult pr = new PrintResult(rs);
		pr.setHead("Preismodell", "Anzahl der Kunden");
		pr.setDescription("Übersicht über die Verteilung der Preismodelle:");
		pr.print();
	}
}
