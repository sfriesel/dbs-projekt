package sqlqueries;

import java.sql.*;

import cli.PrintResult;

import cli.MenuEntryInterface;
import database.*;

public class PlainSQL1B implements MenuEntryInterface {

	@Override
	public String getName() {
		return "1B-Rechnungssummen";
	}

	@Override
	public String getDescription() {
		return "Die Rechnungssummen für alle Kunden für den Monat Januar.";
	}

	@Override
	public void execute() throws Exception {
		DBConnector con = DBConnector.getInstance();
		
		Statement stmt = con.connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			  "SELECT x.customer, c.forename, c.surname, sum(x.price) "
			+ "FROM "
			+ "("
				+ "(SELECT r.customer, r.duration*p.priceperunit AS price "
				+ " FROM rental r JOIN movie m ON r.movie = m.title NATURAL JOIN pricing p "
				+ " WHERE extract(month from r.start) = 1"
				+ ")"
				+ "UNION"
				+ "(SELECT DISTINCT r.customer, 10.00 AS price "
				+ " FROM rental r"
				+ " WHERE extract(month from r.start) = 1 and pricemodel = 'flat'"
				+ ")"
			+ ") x JOIN customer c ON x.customer = c.id "
			+ "GROUP BY x.customer, c.forename, c.surname "
			+ "ORDER BY customer;");
		PrintResult pr = new PrintResult(rs);
		pr.setHead("Kundennr.", "Vorname", "Nachname", "Rechnungssumme (€)");
		pr.setDescription("Rechnungssummen für alle Kunden für den Monat Januar:");
		pr.print();
	}

}
