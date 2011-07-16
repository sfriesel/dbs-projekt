package sqlqueries;

import java.sql.ResultSet;
import java.sql.Statement;
import cli.MenuEntry;
import cli.PrintResult;
import database.DBConnector;

public class PlainSQL1D implements MenuEntry {

	Statement customerStmt;

	@Override
	public String getName() {
		return "1D-Rangliste";
	}

	@Override
	public String getDiscription() {
		return "Ermitteln Sie eine Rangliste aller Kunden, sortiert nach dem Anteil der geliehenen "
				+ "Filme, die (auch) außerhalb der USA gedreht wurde.";
	}

	@Override
	public void execute() throws Exception {

		// DBConnection
		DBConnector con = DBConnector.getInstance();

		customerStmt = con.connection.createStatement();
		ResultSet rs = customerStmt
				.executeQuery("(SELECT DISTINCT r1.customer, "
						+ "ROUND((SELECT CAST("
						+ "(SELECT count(*) FROM Rental r2 WHERE r2.customer = r1.customer AND r2.movie IN "
						+ "(SELECT DISTINCT s.movie FROM Location l, shotin s "
						+ "WHERE s.location = l.location AND l.country <> 'USA')) AS NUMERIC)"
						+ "/(SELECT count(*) FROM Rental r3 WHERE r3.customer = r1.customer)*100), 2) AS summe "
						+ "FROM Rental r1 ORDER BY summe DESC)");

		PrintResult pr = new PrintResult(rs);
		pr.setHead("customer", "ratio");
		pr.setDescription("Rangfolge der Customer, welche Filme ausgeliehen haben, die auch nicht in den "
				+ "USA gedeht worden sind:");
		pr.print();
	}
}
