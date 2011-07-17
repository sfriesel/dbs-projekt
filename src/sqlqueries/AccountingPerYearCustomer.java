package sqlqueries;

import java.sql.*;

import cli.MenuEntryInterface;
import cli.PrintResult;

import cli.MenuEntryInterface;
import database.*;

public class AccountingPerYearCustomer implements MenuEntryInterface {

	@Override
	public String getName() {
		return "Rechnung pro Kalenderjahr und Kunde";
	}

	@Override
	public String getDescription() {
		return "Für jedes Kalenderjahr wird eine Rechnung pro Kunde angefertigt.";
	}

	@Override
	public void execute() throws Exception {
		DBConnector con = DBConnector.getInstance();
		
		Statement stmt = con.connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			  "SELECT x.customer, c.forename, c.surname, x.year, sum(x.price) "
			+ "FROM "
			+ "( "
				+ "(SELECT r.customer, EXTRACT(year from start) AS year, r.duration*p.priceperunit AS price "
				+ " FROM rental r JOIN movie m ON r.movie = m.title NATURAL JOIN pricing p "
				+ ") "
				+ "UNION "
				+ "(SELECT DISTINCT r.customer, EXTRACT(year from start) AS year, count(*)*10.00 AS price "
				+ " FROM rental r "
				+ " WHERE pricemodel = 'flat' "
				+ " GROUP BY r.customer, EXTRACT(year from start), EXTRACT(month from start) "
				+ ") "
			+ ") x JOIN customer c ON x.customer = c.id "
			+ "GROUP BY x.customer, c.forename, c.surname, x.year "
			+ "ORDER BY customer; ");
		PrintResult pr = new PrintResult(rs);
		pr.setHead("Kundennr.", "Vorname", "Nachname", "Jahr", "Rechnungssumme (€)");
		pr.setDescription("Rechnungssummen für alle Kunden für den Monat Januar:");
		pr.print();
	}
}
